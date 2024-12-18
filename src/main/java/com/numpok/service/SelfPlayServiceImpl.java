package com.numpok.service;

import com.numpok.game.common.Decision;
import com.numpok.game.common.Deck;
import com.numpok.game.common.GameHistory;
import com.numpok.game.engine.Game;
import com.numpok.game.engine.GameEngine;
import com.numpok.game.engine.GameState;
import com.numpok.game.engine.GameEventType;
import com.numpok.game.enums.Position;
import com.numpok.game.enums.Street;
import com.numpok.game.hand.PreflopHand;
import com.numpok.game.util.DecisionCorrecter;
import com.numpok.game.ai.DecisionGame;
import com.numpok.game.engine.PlayerInitialState;
import com.numpok.controller.graphql.SelfPlayPropertiesInput;
import com.numpok.controller.graphql.SolutionAliasInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SelfPlayServiceImpl implements SelfPlayService {

    public static final Random random = new Random();
    private final DecisionMakingService decisionMakingService;
    private final GameEngine gameEngine = GameEngine.instance();

    @Autowired
    public SelfPlayServiceImpl(DecisionMakingService decisionMakingService) {
        this.decisionMakingService = decisionMakingService;
    }

    record DeckGame(Deck deck, Game game) {
    }

    @Override
    public List<Game> selfplay(Set<SolutionAliasInput> solutionAliasInputs, SelfPlayPropertiesInput selfPlayPropertiesInput) {
        List<SolutionAliasInput> solutionAliases = new ArrayList<>(solutionAliasInputs);
        Map<String, String> solutionNameByAlias = solutionAliasInputs.stream()
                .collect(Collectors.toMap(SolutionAliasInput::alias, SolutionAliasInput::solutionName));
        int batchSize = selfPlayPropertiesInput.batchSize();
        double bbAmount = selfPlayPropertiesInput.bbAmount();
        GameHistory sbContext = GameHistory.preflopMoveContext(Decision.sb(bbAmount));
        GameHistory bbContext = GameHistory.preflopMoveContext(Decision.bb(bbAmount));
        List<DeckGame> deckGames = IntStream.range(0, batchSize)
                .mapToObj(idx -> {
                    Deck deck = Deck.shuffled();
                    Collections.shuffle(solutionAliases);
                    List<SolutionAliasInput> twoPlayers = solutionAliases.subList(0, 2);
                    EnumMap<Position, PlayerInitialState> playerInitialStatesMap = createMap(deck,
                            twoPlayers,
                            selfPlayPropertiesInput.minInitialStack(),
                            selfPlayPropertiesInput.maxInitialStack());
                    Game game = new Game(playerInitialStatesMap, bbAmount);
                    gameEngine.apply(game, GameEventType.PF_MOVE, 0, sbContext);
                    gameEngine.apply(game, GameEventType.PF_MOVE, 0, bbContext);
                    return new DeckGame(deck, game);
                }).toList();
        List<DeckGame> incompleteGames = deckGames;
        while (!incompleteGames.isEmpty()) {
            incompleteGames = makeDecisions(solutionNameByAlias, incompleteGames);
        }
        return deckGames.stream()
                .map(DeckGame::game)
                .peek(game -> gameEngine.apply(game, GameEventType.END, 0, GameHistory.empty))
                .toList();
    }

    private List<DeckGame> makeDecisions(Map<String, String> solutionNameByAlias, List<DeckGame> games) {
        Map<String, List<DeckGame>> incompleteGamesBySolution = games.stream()
                .collect(Collectors.groupingBy(deckGame -> solutionNameByAlias.get(deckGame.game.getCurrentPlayer().getPlayerName()), Collectors.toList()));
        incompleteGamesBySolution.entrySet().parallelStream()
                .forEach(e -> {
                    List<DeckGame> deckGames = e.getValue();
                    String solutionName = e.getKey();
                    List<DecisionGame> decisionGames = decisionMakingService.makeDecisions(solutionName, deckGames.stream()
                            .map(DeckGame::game)
                            .toList());
                    pushDecisions(decisionGames);
                });
        return games.parallelStream()
                .peek(this::processGameWithIntermediateState)
                .filter(deckGame -> deckGame.game.getState() != GameState.GAME_COMPLETED)
                .toList();
    }

    private void processGameWithIntermediateState(DeckGame deckGame) {
        Game game = deckGame.game;
        Deck deck = deckGame.deck;
        switch (game.getState()) {
            case PREFLOP_COMPLETED ->
                    gameEngine.apply(game, GameEventType.FL_CARDS, 0, GameHistory.flopCardsContext(EnumSet.of(deck.pick(), deck.pick(), deck.pick())));
            case FL_COMPLETED ->
                    gameEngine.apply(game, GameEventType.TN_CARD, 0, GameHistory.turnCardContext(deck.pick()));
            case TN_COMPLETED ->
                    gameEngine.apply(game, GameEventType.RV_CARD, 0, GameHistory.riverCardContext(deck.pick()));
            case PREFLOP_A_COMPLETED -> {
                gameEngine.apply(game, GameEventType.FL_CARDS, 0, GameHistory.flopCardsContext(EnumSet.of(deck.pick(), deck.pick(), deck.pick())));
                gameEngine.apply(game, GameEventType.TN_CARD, 0, GameHistory.turnCardContext(deck.pick()));
                gameEngine.apply(game, GameEventType.RV_CARD, 0, GameHistory.riverCardContext(deck.pick()));
            }
            case FL_A_COMPLETED -> {
                gameEngine.apply(game, GameEventType.TN_CARD, 0, GameHistory.turnCardContext(deck.pick()));
                gameEngine.apply(game, GameEventType.RV_CARD, 0, GameHistory.riverCardContext(deck.pick()));
            }
            case TN_A_COMPLETED -> {
                gameEngine.apply(game, GameEventType.RV_CARD, 0, GameHistory.riverCardContext(deck.pick()));
            }
        }
    }

    private void pushDecisions(List<DecisionGame> decisionGames) {
        decisionGames.parallelStream().forEach(decisionGame -> {
            Game game = decisionGame.game();
            Decision srcDecision = decisionGame.decision();
            Decision decision = DecisionCorrecter.correct(game, srcDecision);
            Street street = game.getStreet();
            switch (street) {
                case PF -> gameEngine.apply(game, GameEventType.PF_MOVE, 0, GameHistory.preflopMoveContext(decision));
                case FL -> gameEngine.apply(game, GameEventType.FL_MOVE, 0, GameHistory.flopMoveContext(decision));
                case TN -> gameEngine.apply(game, GameEventType.TN_MOVE, 0, GameHistory.turnMoveContext(decision));
                case RV -> gameEngine.apply(game, GameEventType.RV_MOVE, 0, GameHistory.riverMoveContext(decision));
            }
        });
    }

    private EnumMap<Position, PlayerInitialState> createMap(Deck deck, List<SolutionAliasInput> twoPlayers, double minInitialStack, double maxInitialStack) {
        EnumMap<Position, PlayerInitialState> map = new EnumMap<>(Position.class);
        map.put(Position.BB, new PlayerInitialState(random.nextDouble(minInitialStack, maxInitialStack),
                PreflopHand.of(deck.pick(), deck.pick()),
                twoPlayers.get(0).alias()));
        map.put(Position.BTN, new PlayerInitialState(random.nextDouble(minInitialStack, maxInitialStack),
                PreflopHand.of(deck.pick(), deck.pick()),
                twoPlayers.get(1).alias()));
        return map;
    }
}
