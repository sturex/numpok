package com.numpok.game.engine;

import com.numpok.core.fsm.HashMapStateMachine;
import com.numpok.game.common.Decision;
import com.numpok.game.common.GameHistory;
import com.numpok.game.enums.DecisionType;

public class GameEngine extends HashMapStateMachine<GameEventType, GameState, Game, GameHistory> {
    GameEngine() {
        this
                .addTransition(GameState.SB,
                        GameEventType.PF_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.preflopDecisions().get(idx);
                            game.accept(decision);
                            return GameState.BB;
                        })
                .addTransition(GameState.BB,
                        GameEventType.PF_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.preflopDecisions().get(idx);
                            game.accept(decision);
                            return GameState.PF_FIRST;
                        })
                .addTransition(GameState.PF_FIRST,
                        GameEventType.PF_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.preflopDecisions().get(idx);
                            game.accept(decision);
                            return switch (decision.decisionType()) {
                                case FOLD -> GameState.GAME_COMPLETED;
                                case CALL -> GameState.PREFLOP_COMPLETED;
                                case CALL_ALLIN -> GameState.PREFLOP_A_COMPLETED;
                                case RAISE_ALLIN -> GameState.PF_A;
                                case RAISE -> GameState.PF_R;
                                default -> throwOnUnexpectedValue(game, gameHistory, decision);
                            };
                        })
                .addTransition(GameState.PF_R,
                        GameEventType.PF_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.preflopDecisions().get(idx);
                            game.accept(decision);
                            return switch (decision.decisionType()) {
                                case FOLD -> GameState.GAME_COMPLETED;
                                case CALL -> GameState.PREFLOP_COMPLETED;
                                case CALL_ALLIN -> GameState.PREFLOP_A_COMPLETED;
                                case RAISE_ALLIN -> GameState.PF_A;
                                case RAISE -> GameState.PF_RR;
                                default -> throwOnUnexpectedValue(game, gameHistory, decision);
                            };
                        })
                .addTransition(GameState.PF_RR,
                        GameEventType.PF_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.preflopDecisions().get(idx);
                            game.accept(decision);
                            return switch (decision.decisionType()) {
                                case FOLD -> GameState.GAME_COMPLETED;
                                case CALL -> GameState.PREFLOP_COMPLETED;
                                case CALL_ALLIN -> GameState.PREFLOP_A_COMPLETED;
                                case RAISE_ALLIN -> GameState.PF_A;
                                case RAISE -> GameState.PF_RRR;
                                default -> throwOnUnexpectedValue(game, gameHistory, decision);
                            };
                        })
                .addTransition(GameState.PF_RRR,
                        GameEventType.PF_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.preflopDecisions().get(idx);
                            game.accept(decision);
                            return switch (decision.decisionType()) {
                                case FOLD -> GameState.GAME_COMPLETED;
                                case CALL -> GameState.PREFLOP_COMPLETED;
                                case CALL_ALLIN -> GameState.PREFLOP_A_COMPLETED;
                                case RAISE_ALLIN -> GameState.PF_A;
                                case RAISE -> GameState.PF_RRR;
                                default -> throwOnUnexpectedValue(game, gameHistory, decision);
                            };
                        })
                .addTransition(GameState.FL_FIRST,
                        GameEventType.FL_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.flopDecisions().get(idx);
                            game.accept(decision);
                            return switch (decision.decisionType()) {
                                case FOLD -> GameState.GAME_COMPLETED;
                                case CHECK -> GameState.FL_C;
                                case BET_ALLIN -> GameState.FL_A;
                                case BET -> GameState.FL_B;
                                default -> throwOnUnexpectedValue(game, gameHistory, decision);
                            };
                        })
                .addTransition(GameState.FL_C,
                        GameEventType.FL_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.flopDecisions().get(idx);
                            game.accept(decision);
                            return switch (decision.decisionType()) {
                                case FOLD -> GameState.GAME_COMPLETED;
                                case CHECK -> GameState.FL_COMPLETED;
                                case BET_ALLIN -> GameState.FL_A;
                                case BET -> GameState.FL_CB;
                                default -> throwOnUnexpectedValue(game, gameHistory, decision);
                            };
                        })
                .addTransition(GameState.FL_CB,
                        GameEventType.FL_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.flopDecisions().get(idx);
                            game.accept(decision);
                            return switch (decision.decisionType()) {
                                case FOLD -> GameState.GAME_COMPLETED;
                                case CALL -> GameState.FL_COMPLETED;
                                case CALL_ALLIN -> GameState.FL_A_COMPLETED;
                                case RAISE -> GameState.FL_CBR;
                                case RAISE_ALLIN -> GameState.FL_A;
                                default -> throwOnUnexpectedValue(game, gameHistory, decision);
                            };
                        })
                .addTransition(GameState.FL_CBR,
                        GameEventType.FL_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.flopDecisions().get(idx);
                            game.accept(decision);
                            return switch (decision.decisionType()) {
                                case FOLD -> GameState.GAME_COMPLETED;
                                case CALL -> GameState.FL_COMPLETED;
                                case CALL_ALLIN -> GameState.FL_A_COMPLETED;
                                case RAISE -> GameState.FL_MR;
                                case RAISE_ALLIN -> GameState.FL_A;
                                default -> throwOnUnexpectedValue(game, gameHistory, decision);
                            };
                        })
                .addTransition(GameState.FL_B,
                        GameEventType.FL_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.flopDecisions().get(idx);
                            game.accept(decision);
                            return switch (decision.decisionType()) {
                                case FOLD -> GameState.GAME_COMPLETED;
                                case CALL -> GameState.FL_COMPLETED;
                                case CALL_ALLIN -> GameState.FL_A_COMPLETED;
                                case RAISE -> GameState.FL_BR;
                                case RAISE_ALLIN -> GameState.FL_A;
                                default -> throwOnUnexpectedValue(game, gameHistory, decision);
                            };
                        })
                .addTransition(GameState.FL_BR,
                        GameEventType.FL_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.flopDecisions().get(idx);
                            game.accept(decision);
                            return switch (decision.decisionType()) {
                                case FOLD -> GameState.GAME_COMPLETED;
                                case CALL -> GameState.FL_COMPLETED;
                                case CALL_ALLIN -> GameState.FL_A_COMPLETED;
                                case RAISE -> GameState.FL_MR;
                                case RAISE_ALLIN -> GameState.FL_A;
                                default -> throwOnUnexpectedValue(game, gameHistory, decision);
                            };
                        })
                .addTransition(GameState.FL_MR,
                        GameEventType.FL_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.flopDecisions().get(idx);
                            game.accept(decision);
                            return switch (decision.decisionType()) {
                                case FOLD -> GameState.GAME_COMPLETED;
                                case CALL -> GameState.FL_COMPLETED;
                                case CALL_ALLIN -> GameState.FL_A_COMPLETED;
                                case RAISE -> GameState.FL_MR;
                                case RAISE_ALLIN -> GameState.FL_A;
                                default -> throwOnUnexpectedValue(game, gameHistory, decision);
                            };
                        })
                .addTransition(GameState.TN_FIRST,
                        GameEventType.TN_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.turnDecisions().get(idx);
                            game.accept(decision);
                            return switch (decision.decisionType()) {
                                case FOLD -> GameState.GAME_COMPLETED;
                                case CHECK -> GameState.TN_C;
                                case BET_ALLIN -> GameState.TN_A;
                                case BET -> GameState.TN_B;
                                default -> throwOnUnexpectedValue(game, gameHistory, decision);
                            };
                        })
                .addTransition(GameState.TN_C,
                        GameEventType.TN_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.turnDecisions().get(idx);
                            game.accept(decision);
                            return switch (decision.decisionType()) {
                                case FOLD -> GameState.GAME_COMPLETED;
                                case CHECK -> GameState.TN_COMPLETED;
                                case BET_ALLIN -> GameState.TN_A;
                                case BET -> GameState.TN_CB;
                                default -> throwOnUnexpectedValue(game, gameHistory, decision);
                            };
                        })
                .addTransition(GameState.TN_CB,
                        GameEventType.TN_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.turnDecisions().get(idx);
                            game.accept(decision);
                            return switch (decision.decisionType()) {
                                case FOLD -> GameState.GAME_COMPLETED;
                                case CALL -> GameState.TN_COMPLETED;
                                case CALL_ALLIN -> GameState.TN_A_COMPLETED;
                                case RAISE -> GameState.TN_CBR;
                                case RAISE_ALLIN -> GameState.TN_A;
                                default -> throwOnUnexpectedValue(game, gameHistory, decision);
                            };
                        })
                .addTransition(GameState.TN_CBR,
                        GameEventType.TN_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.turnDecisions().get(idx);
                            game.accept(decision);
                            return switch (decision.decisionType()) {
                                case FOLD -> GameState.GAME_COMPLETED;
                                case CALL -> GameState.TN_COMPLETED;
                                case CALL_ALLIN -> GameState.TN_A_COMPLETED;
                                case RAISE -> GameState.TN_MR;
                                case RAISE_ALLIN -> GameState.TN_A;
                                default -> throwOnUnexpectedValue(game, gameHistory, decision);
                            };
                        })
                .addTransition(GameState.TN_B,
                        GameEventType.TN_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.turnDecisions().get(idx);
                            game.accept(decision);
                            return switch (decision.decisionType()) {
                                case FOLD -> GameState.GAME_COMPLETED;
                                case CALL -> GameState.TN_COMPLETED;
                                case CALL_ALLIN -> GameState.TN_A_COMPLETED;
                                case RAISE -> GameState.TN_BR;
                                case RAISE_ALLIN -> GameState.TN_A;
                                default -> throwOnUnexpectedValue(game, gameHistory, decision);
                            };
                        })
                .addTransition(GameState.TN_BR,
                        GameEventType.TN_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.turnDecisions().get(idx);
                            game.accept(decision);
                            return switch (decision.decisionType()) {
                                case FOLD -> GameState.GAME_COMPLETED;
                                case CALL -> GameState.TN_COMPLETED;
                                case CALL_ALLIN -> GameState.TN_A_COMPLETED;
                                case RAISE -> GameState.TN_MR;
                                case RAISE_ALLIN -> GameState.TN_A;
                                default -> throwOnUnexpectedValue(game, gameHistory, decision);
                            };
                        })
                .addTransition(GameState.TN_MR,
                        GameEventType.TN_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.turnDecisions().get(idx);
                            game.accept(decision);
                            return switch (decision.decisionType()) {
                                case FOLD -> GameState.GAME_COMPLETED;
                                case CALL -> GameState.TN_COMPLETED;
                                case CALL_ALLIN -> GameState.TN_A_COMPLETED;
                                case RAISE -> GameState.TN_MR;
                                case RAISE_ALLIN -> GameState.TN_A;
                                default -> throwOnUnexpectedValue(game, gameHistory, decision);
                            };
                        })
                .addTransition(GameState.RV_FIRST,
                        GameEventType.RV_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.riverDecisions().get(idx);
                            game.accept(decision);
                            return switch (decision.decisionType()) {
                                case FOLD -> GameState.GAME_COMPLETED;
                                case CHECK -> GameState.RV_C;
                                case BET_ALLIN -> GameState.RV_A;
                                case BET -> GameState.RV_B;
                                default -> throwOnUnexpectedValue(game, gameHistory, decision);
                            };
                        })
                .addTransition(GameState.RV_C,
                        GameEventType.RV_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.riverDecisions().get(idx);
                            game.accept(decision);
                            return switch (decision.decisionType()) {
                                case FOLD, CHECK -> GameState.GAME_COMPLETED;
                                case BET_ALLIN -> GameState.RV_A;
                                case BET -> GameState.RV_CB;
                                default -> throwOnUnexpectedValue(game, gameHistory, decision);
                            };
                        })
                .addTransition(GameState.RV_B,
                        GameEventType.RV_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.riverDecisions().get(idx);
                            game.accept(decision);
                            return switch (decision.decisionType()) {
                                case FOLD, CALL, CALL_ALLIN -> GameState.GAME_COMPLETED;
                                case RAISE -> GameState.RV_BR;
                                case RAISE_ALLIN -> GameState.RV_A;
                                default -> throwOnUnexpectedValue(game, gameHistory, decision);
                            };
                        })
                .addTransition(GameState.RV_CB,
                        GameEventType.RV_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.riverDecisions().get(idx);
                            game.accept(decision);
                            return switch (decision.decisionType()) {
                                case FOLD, CALL, CALL_ALLIN -> GameState.GAME_COMPLETED;
                                case RAISE -> GameState.RV_CBR;
                                case RAISE_ALLIN -> GameState.RV_A;
                                default -> throwOnUnexpectedValue(game, gameHistory, decision);
                            };
                        })
                .addTransition(GameState.RV_CBR,
                        GameEventType.RV_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.riverDecisions().get(idx);
                            game.accept(decision);
                            return switch (decision.decisionType()) {
                                case FOLD, CALL, CALL_ALLIN -> GameState.GAME_COMPLETED;
                                case RAISE -> GameState.RV_MR;
                                case RAISE_ALLIN -> GameState.RV_A;
                                default -> throwOnUnexpectedValue(game, gameHistory, decision);
                            };
                        })
                .addTransition(GameState.RV_BR,
                        GameEventType.RV_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.riverDecisions().get(idx);
                            game.accept(decision);
                            return switch (decision.decisionType()) {
                                case FOLD, CALL, CALL_ALLIN -> GameState.GAME_COMPLETED;
                                case RAISE -> GameState.RV_MR;
                                case RAISE_ALLIN -> GameState.RV_A;
                                default -> throwOnUnexpectedValue(game, gameHistory, decision);
                            };
                        })
                .addTransition(GameState.RV_MR,
                        GameEventType.RV_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.riverDecisions().get(idx);
                            game.accept(decision);
                            return switch (decision.decisionType()) {
                                case FOLD, CALL, CALL_ALLIN -> GameState.GAME_COMPLETED;
                                case RAISE -> GameState.RV_MR;
                                case RAISE_ALLIN -> GameState.RV_A;
                                default -> throwOnUnexpectedValue(game, gameHistory, decision);
                            };
                        })
                .addTransition(GameState.PF_A,
                        GameEventType.PF_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.preflopDecisions().get(idx);
                            game.accept(decision);
                            return decision.decisionType() == DecisionType.FOLD ? GameState.GAME_COMPLETED : GameState.PREFLOP_A_COMPLETED;
                        })
                .addTransition(GameState.FL_A,
                        GameEventType.FL_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.flopDecisions().get(idx);
                            game.accept(decision);
                            return decision.decisionType() == DecisionType.FOLD ? GameState.GAME_COMPLETED : GameState.FL_A_COMPLETED;
                        })
                .addTransition(GameState.TN_A,
                        GameEventType.TN_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.turnDecisions().get(idx);
                            game.accept(decision);
                            return decision.decisionType() == DecisionType.FOLD ? GameState.GAME_COMPLETED : GameState.TN_A_COMPLETED;
                        })
                .addTransition(GameState.RV_A,
                        GameEventType.RV_MOVE,
                        (game, idx, gameHistory) -> {
                            Decision decision = gameHistory.riverDecisions().get(idx);
                            game.accept(decision);
                            return GameState.GAME_COMPLETED;
                        })
                .addTransition(GameState.PREFLOP_COMPLETED,
                        GameEventType.FL_CARDS,
                        (game, idx, gameHistory) -> {
                            game.computePreflopCompletedFeatures(gameHistory.flopCards());
                            return GameState.FL_FIRST;
                        })
                .addTransition(GameState.FL_COMPLETED,
                        GameEventType.TN_CARD,
                        (game, idx, gameHistory) -> {
                            game.computeFlopCompletedFeatures(gameHistory.turnCard());
                            return GameState.TN_FIRST;
                        })
                .addTransition(GameState.TN_COMPLETED,
                        GameEventType.RV_CARD,
                        (game, idx, gameHistory) -> {
                            game.computeTurnCompletedFeatures(gameHistory.riverCard());
                            return GameState.RV_FIRST;
                        })
                .addTransition(GameState.PREFLOP_A_COMPLETED,
                        GameEventType.FL_CARDS,
                        (game, idx, gameHistory) -> {
                            game.computePreflopCompletedFeatures(gameHistory.flopCards());
                            return GameState.FL_A_COMPLETED;
                        })
                .addTransition(GameState.FL_A_COMPLETED,
                        GameEventType.TN_CARD,
                        (game, idx, gameHistory) -> {
                            game.computeFlopCompletedFeatures(gameHistory.turnCard());
                            return GameState.TN_A_COMPLETED;
                        })
                .addTransition(GameState.TN_A_COMPLETED,
                        GameEventType.RV_CARD,
                        (game, idx, gameHistory) -> {
                            game.computeTurnCompletedFeatures(gameHistory.riverCard());
                            return GameState.GAME_COMPLETED;
                        })
                .addTransition(GameState.GAME_COMPLETED,
                        GameEventType.END,
                        (game, idx, gameHistory) -> {
                            game.computePL();
                            return GameState.END;
                        })
        ;
    }

    private static final GameEngine gameEngine = new GameEngine();

    public static GameEngine instance() {
        return gameEngine;
    }

    private GameState throwOnUnexpectedValue(Game game, GameHistory gameHistory, Decision decision) {
        throw new IllegalStateException("Unexpected decisionType: " + decision.decisionType() + ", gameHistory: " + gameHistory + ", game: " + game);
    }
}
