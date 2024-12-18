package com.numpok.game.engine;

import com.numpok.core.fsm.Stateful;
import com.numpok.game.common.Decision;
import com.numpok.game.enums.Card;
import com.numpok.game.enums.DecisionType;
import com.numpok.game.enums.Position;
import com.numpok.game.enums.Street;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Game implements Stateful<GameState> {
    private Street street = Street.PF;
    private double currentPot = 0;
    private double uncalledBet = 0;
    private double totalBet = 0;
    private GameState state = GameState.SB;
    private final double bbAmount;
    private final Player[] players;
    private int playerTurnIdx = 0;
    private final EnumMap<Street, List<Decision>> decisionsByStreet;
    private final EnumMap<Position, Player> playerByPosition;
    private final EnumSet<Card> boardCards = EnumSet.noneOf(Card.class);

    public Game(EnumMap<Position, PlayerInitialState> playerInitialStatesMap, double bbAmount) {
        this.bbAmount = bbAmount;
        playerByPosition = playerInitialStatesMap.entrySet().stream().map(e -> {
            PlayerInitialState playerInitialState = e.getValue();
            return new Player(e.getKey(), playerInitialState.playerName(), playerInitialState.preflopHand(), playerInitialState.stack());
        }).collect(Collectors.toMap(Player::getPosition, Function.identity(), (o, o2) -> o, () -> new EnumMap<>(Position.class)));
        players = new Player[]{playerByPosition.get(Position.BTN), playerByPosition.get(Position.BB)};
        decisionsByStreet = Arrays.stream(Street.values()).collect(Collectors.toMap(s -> s, s -> new ArrayList<>(), (o, o2) -> o, () -> new EnumMap<>(Street.class)));
    }

    private Player btnPlayer() {
        return playerByPosition.get(Position.BTN);
    }

    private Player bbPlayer() {
        return playerByPosition.get(Position.BB);
    }

    @Override
    public void setState(GameState state) {
        this.state = state;
    }

    @Override
    public GameState getState() {
        return state;
    }

    public EnumSet<Card> getBoardCards() {
        return boardCards;
    }

    public Player[] getPlayers() {
        return players;
    }

    public double getCurrentPot() {
        return currentPot;
    }

    public double getBbAmount() {
        return bbAmount;
    }

    public double getUncalledBet() {
        return uncalledBet;
    }

    public double getTotalBet() {
        return totalBet;
    }

    public Player getCurrentPlayerOpponent() {
        return players[(playerTurnIdx + 1) % 2];
    }

    public Player getCurrentPlayer() {
        return players[playerTurnIdx];
    }

    public Street getStreet() {
        return street;
    }

    void accept(Decision decision) {
        double amount = decision.amount();
        currentPot += amount;
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.accept(decision);
        totalBet += amount;
        uncalledBet = Math.abs(players[0].getTotalInvested() - players[1].getTotalInvested());
        setNextPlayerTurn();
        decisionsByStreet.get(street).add(decision);
        if (decision.decisionType() == DecisionType.FOLD) {
            setPlayerStatesOnFoldDecision();
        }
    }

    private void setPlayerStatesOnFoldDecision() {
        switch (street) {
            case PF -> {
                getCurrentPlayer().setState(PlayerState.FOLD_BY_PREFLOP);
                getCurrentPlayerOpponent().setState(PlayerState.WIN_BY_PREFLOP);
            }
            case FL -> {
                getCurrentPlayer().setState(PlayerState.FOLD_BY_FLOP);
                getCurrentPlayerOpponent().setState(PlayerState.WIN_BY_FLOP);
            }
            case TN -> {
                getCurrentPlayer().setState(PlayerState.FOLD_BY_TURN);
                getCurrentPlayerOpponent().setState(PlayerState.WIN_BY_TURN);
            }
            case RV -> {
                getCurrentPlayer().setState(PlayerState.FOLD_BY_RIVER);
                getCurrentPlayerOpponent().setState(PlayerState.WIN_BY_RIVER);
            }
        }
    }

    private void setNextPlayerTurn() {
        playerTurnIdx = (playerTurnIdx + 1) % players.length;
    }

    void computePreflopCompletedFeatures(EnumSet<Card> cards) {
        boardCards.addAll(cards);
        resetPlayerTurnIdx();
        street = Street.FL;
        Arrays.stream(players).forEach(p -> p.acceptFlopCards(cards));
    }

    void computeFlopCompletedFeatures(Card card) {
        boardCards.add(card);
        resetPlayerTurnIdx();
        street = Street.TN;
        Arrays.stream(players).forEach(p -> p.acceptTurnCard(card));
    }

    void computeTurnCompletedFeatures(Card card) {
        boardCards.add(card);
        resetPlayerTurnIdx();
        street = Street.RV;
        Arrays.stream(players).forEach(p -> p.acceptRiverCard(card));
    }

    void computePL() {
        if (PlayerState.foldStates.contains(btnPlayer().getState())) {
            bbPlayer().setTotalCollected(currentPot);
        } else if (PlayerState.foldStates.contains(bbPlayer().getState())) {
            btnPlayer().setTotalCollected(currentPot);
        } else {
            int bbPlayerPoints = bbPlayer().getPoints(Street.RV);
            int btnPlayerPoints = btnPlayer().getPoints(Street.RV);
            if (bbPlayerPoints > btnPlayerPoints) {
                bbPlayer().setTotalCollected(currentPot);
            } else if (btnPlayerPoints > bbPlayerPoints) {
                btnPlayer().setTotalCollected(currentPot);
            } else {
                bbPlayer().setTotalCollected(currentPot / 2);
                btnPlayer().setTotalCollected(currentPot / 2);
            }
        }
    }

    private void resetPlayerTurnIdx() {
        playerTurnIdx = 0;
    }
}
