package com.numpok.game.ai;

import com.numpok.game.common.Decision;
import com.numpok.game.engine.Game;
import com.numpok.game.engine.GameState;
import com.numpok.game.engine.Player;
import com.numpok.game.enums.Card;
import com.numpok.game.enums.Street;
import com.numpok.game.util.PointsEvaluator;

import java.util.Random;

public enum DumbBots implements DecisionMakingSystem {

    RANDY {
        private final Random random = new Random();

        @Override
        public Decision makeDecision(Game game) {
            GameState gameState = game.getState();
            double rnd = random.nextDouble();
            return switch (gameState) {
                case PF_FIRST -> {
                    if (rnd < 0.4) {
                        yield Decision.raise(game.getBbAmount() * 2);
                    } else if (rnd < 0.5) {
                        yield Decision.raise(game.getBbAmount() * 2.5);
                    } else if (rnd < 0.6) {
                        yield Decision.raise(game.getBbAmount() * 3);
                    } else if (rnd < 0.8) {
                        yield Decision.call(game.getUncalledBet());
                    } else if (rnd < 0.95) {
                        yield Decision.fold;
                    } else {
                        yield Decision.raiseAllin(game.getCurrentPlayer().getCurrentStack());
                    }
                }
                case PF_R -> {
                    if (rnd < 0.3) {
                        yield Decision.raise(game.getUncalledBet() * 2);
                    } else if (rnd < 0.5) {
                        yield Decision.raise(game.getUncalledBet() * 3);
                    } else if (rnd < 0.7) {
                        yield Decision.raise(game.getUncalledBet() * 4);
                    } else if (rnd < 0.9) {
                        yield Decision.call(game.getUncalledBet());
                    } else if (rnd < 0.95) {
                        yield Decision.fold;
                    } else {
                        yield Decision.raiseAllin(game.getCurrentPlayer().getCurrentStack());
                    }
                }
                case PF_RR -> {
                    if (rnd < 0.5) {
                        yield Decision.call(game.getUncalledBet());
                    } else if (rnd < 0.6) {
                        yield Decision.raise(game.getUncalledBet() * 2);
                    } else if (rnd < 0.8) {
                        yield Decision.fold;
                    } else {
                        yield Decision.raiseAllin(game.getCurrentPlayer().getCurrentStack());
                    }
                }
                case PF_RRR -> {
                    if (rnd < 0.6) {
                        yield Decision.call(game.getUncalledBet());
                    } else if (rnd < 0.8) {
                        yield Decision.fold;
                    } else {
                        yield Decision.raiseAllin(game.getCurrentPlayer().getCurrentStack());
                    }
                }
                case PF_A -> {
                    if (rnd < 0.4) {
                        yield Decision.call(game.getUncalledBet());
                    } else {
                        yield Decision.fold;
                    }
                }
                case FL_FIRST -> {
                    if (rnd < 0.2) {
                        yield Decision.bet(game.getCurrentPot() * 0.2);
                    } else if (rnd < 0.3) {
                        yield Decision.bet(game.getCurrentPot() * 0.33);
                    } else if (rnd < 0.4) {
                        yield Decision.bet(game.getCurrentPot() * 0.5);
                    } else if (rnd < 0.5) {
                        yield Decision.bet(game.getCurrentPot() * 0.66);
                    } else if (rnd < 0.55) {
                        yield Decision.bet(game.getCurrentPot() * 1.0);
                    } else if (rnd < 0.65) {
                        yield Decision.bet(game.getCurrentPot() * 1.5);
                    } else if (rnd < 0.97) {
                        yield Decision.check;
                    } else {
                        yield Decision.betAllin(game.getCurrentPlayer().getCurrentStack());
                    }
                }
                case FL_B -> {
                    if (rnd < 0.45) {
                        yield Decision.fold;
                    } else if (rnd < 0.5) {
                        yield Decision.raise(game.getUncalledBet() * 2);
                    } else if (rnd < 0.55) {
                        yield Decision.raise(game.getUncalledBet() * 3);
                    } else if (rnd < 0.6) {
                        yield Decision.raise(game.getUncalledBet() * 4);
                    } else if (rnd < 0.95) {
                        yield Decision.call(game.getUncalledBet());
                    } else {
                        yield Decision.raiseAllin(game.getCurrentPlayer().getCurrentStack());
                    }
                }
                case FL_C -> {
                    if (rnd < 0.25) {
                        yield Decision.bet(game.getCurrentPot() * 0.25);
                    } else if (rnd < 0.35) {
                        yield Decision.bet(game.getCurrentPot() * 0.5);
                    } else if (rnd < 0.45) {
                        yield Decision.bet(game.getCurrentPot() * 0.75);
                    } else if (rnd < 0.55) {
                        yield Decision.bet(game.getCurrentPot() * 1.0);
                    } else if (rnd < 0.65) {
                        yield Decision.bet(game.getCurrentPot() * 1.25);
                    } else if (rnd < 0.97) {
                        yield Decision.check;
                    } else {
                        yield Decision.betAllin(game.getCurrentPlayer().getCurrentStack());
                    }
                }
                case FL_CB -> {
                    if (rnd < 0.3) {
                        yield Decision.fold;
                    } else if (rnd < 0.4) {
                        yield Decision.raise(game.getUncalledBet() * 2);
                    } else if (rnd < 0.5) {
                        yield Decision.raise(game.getUncalledBet() * 3);
                    } else if (rnd < 0.6) {
                        yield Decision.raise(game.getUncalledBet() * 4);
                    } else if (rnd < 0.98) {
                        yield Decision.call(game.getUncalledBet());
                    } else {
                        yield Decision.raiseAllin(game.getCurrentPlayer().getCurrentStack());
                    }
                }
                case FL_BR -> {
                    if (rnd < 0.6) {
                        yield Decision.fold;
                    } else if (rnd < 0.65) {
                        yield Decision.raise(game.getUncalledBet() * 2.5);
                    } else if (rnd < 0.7) {
                        yield Decision.raise(game.getUncalledBet() * 4);
                    } else if (rnd < 0.75) {
                        yield Decision.raise(game.getUncalledBet() * 4);
                    } else if (rnd < 0.98) {
                        yield Decision.call(game.getUncalledBet());
                    } else {
                        yield Decision.raiseAllin(game.getCurrentPlayer().getCurrentStack());
                    }
                }
                case FL_CBR -> {
                    if (rnd < 0.5) {
                        yield Decision.fold;
                    } else if (rnd < 0.55) {
                        yield Decision.raise(game.getUncalledBet() * 2.5);
                    } else if (rnd < 0.6) {
                        yield Decision.raise(game.getUncalledBet() * 4);
                    } else if (rnd < 0.65) {
                        yield Decision.raise(game.getUncalledBet() * 4);
                    } else if (rnd < 0.97) {
                        yield Decision.call(game.getUncalledBet());
                    } else {
                        yield Decision.raiseAllin(game.getCurrentPlayer().getCurrentStack());
                    }
                }
                case FL_MR -> {
                    if (rnd < 0.4) {
                        yield Decision.fold;
                    } else if (rnd < 0.55) {
                        yield Decision.call(game.getUncalledBet());
                    } else {
                        yield Decision.raiseAllin(game.getCurrentPlayer().getCurrentStack());
                    }
                }
                case FL_A -> {
                    if (rnd < 0.55) {
                        yield Decision.fold;
                    } else {
                        yield Decision.call(game.getUncalledBet());
                    }
                }
                case TN_FIRST -> {
                    if (rnd < 0.2) {
                        yield Decision.bet(game.getCurrentPot() * 0.2);
                    } else if (rnd < 0.3) {
                        yield Decision.bet(game.getCurrentPot() * 0.33);
                    } else if (rnd < 0.4) {
                        yield Decision.bet(game.getCurrentPot() * 0.5);
                    } else if (rnd < 0.5) {
                        yield Decision.bet(game.getCurrentPot() * 0.66);
                    } else if (rnd < 0.55) {
                        yield Decision.bet(game.getCurrentPot() * 1.0);
                    } else if (rnd < 0.65) {
                        yield Decision.bet(game.getCurrentPot() * 1.5);
                    } else if (rnd < 0.97) {
                        yield Decision.check;
                    } else {
                        yield Decision.betAllin(game.getCurrentPlayer().getCurrentStack());
                    }
                }
                case TN_B -> {
                    if (rnd < 0.45) {
                        yield Decision.fold;
                    } else if (rnd < 0.5) {
                        yield Decision.raise(game.getUncalledBet() * 2);
                    } else if (rnd < 0.55) {
                        yield Decision.raise(game.getUncalledBet() * 3);
                    } else if (rnd < 0.6) {
                        yield Decision.raise(game.getUncalledBet() * 4);
                    } else if (rnd < 0.95) {
                        yield Decision.call(game.getUncalledBet());
                    } else {
                        yield Decision.raiseAllin(game.getCurrentPlayer().getCurrentStack());
                    }
                }
                case TN_C -> {
                    if (rnd < 0.25) {
                        yield Decision.bet(game.getCurrentPot() * 0.25);
                    } else if (rnd < 0.35) {
                        yield Decision.bet(game.getCurrentPot() * 0.5);
                    } else if (rnd < 0.45) {
                        yield Decision.bet(game.getCurrentPot() * 0.75);
                    } else if (rnd < 0.55) {
                        yield Decision.bet(game.getCurrentPot() * 1.0);
                    } else if (rnd < 0.65) {
                        yield Decision.bet(game.getCurrentPot() * 1.25);
                    } else if (rnd < 0.97) {
                        yield Decision.check;
                    } else {
                        yield Decision.betAllin(game.getCurrentPlayer().getCurrentStack());
                    }
                }
                case TN_CB -> {
                    if (rnd < 0.3) {
                        yield Decision.fold;
                    } else if (rnd < 0.4) {
                        yield Decision.raise(game.getUncalledBet() * 2);
                    } else if (rnd < 0.5) {
                        yield Decision.raise(game.getUncalledBet() * 3);
                    } else if (rnd < 0.6) {
                        yield Decision.raise(game.getUncalledBet() * 4);
                    } else if (rnd < 0.98) {
                        yield Decision.call(game.getUncalledBet());
                    } else {
                        yield Decision.raiseAllin(game.getCurrentPlayer().getCurrentStack());
                    }
                }
                case TN_BR -> {
                    if (rnd < 0.6) {
                        yield Decision.fold;
                    } else if (rnd < 0.65) {
                        yield Decision.raise(game.getUncalledBet() * 2.5);
                    } else if (rnd < 0.7) {
                        yield Decision.raise(game.getUncalledBet() * 4);
                    } else if (rnd < 0.75) {
                        yield Decision.raise(game.getUncalledBet() * 4);
                    } else if (rnd < 0.98) {
                        yield Decision.call(game.getUncalledBet());
                    } else {
                        yield Decision.raiseAllin(game.getCurrentPlayer().getCurrentStack());
                    }
                }
                case TN_CBR -> {
                    if (rnd < 0.55) {
                        yield Decision.fold;
                    } else if (rnd < 0.6) {
                        yield Decision.raise(game.getUncalledBet() * 2.5);
                    } else if (rnd < 0.65) {
                        yield Decision.raise(game.getUncalledBet() * 4);
                    } else if (rnd < 0.70) {
                        yield Decision.raise(game.getUncalledBet() * 4);
                    } else if (rnd < 0.98) {
                        yield Decision.call(game.getUncalledBet());
                    } else {
                        yield Decision.raiseAllin(game.getCurrentPlayer().getCurrentStack());
                    }
                }
                case TN_MR -> {
                    if (rnd < 0.4) {
                        yield Decision.fold;
                    } else if (rnd < 0.55) {
                        yield Decision.call(game.getUncalledBet());
                    } else {
                        yield Decision.raiseAllin(game.getCurrentPlayer().getCurrentStack());
                    }
                }
                case TN_A -> {
                    if (rnd < 0.55) {
                        yield Decision.fold;
                    } else {
                        yield Decision.call(game.getUncalledBet());
                    }
                }
                case RV_FIRST -> {
                    if (rnd < 0.2) {
                        yield Decision.bet(game.getCurrentPot() * 0.2);
                    } else if (rnd < 0.3) {
                        yield Decision.bet(game.getCurrentPot() * 0.33);
                    } else if (rnd < 0.4) {
                        yield Decision.bet(game.getCurrentPot() * 0.5);
                    } else if (rnd < 0.5) {
                        yield Decision.bet(game.getCurrentPot() * 0.66);
                    } else if (rnd < 0.55) {
                        yield Decision.bet(game.getCurrentPot() * 1.0);
                    } else if (rnd < 0.65) {
                        yield Decision.bet(game.getCurrentPot() * 1.5);
                    } else if (rnd < 0.97) {
                        yield Decision.check;
                    } else {
                        yield Decision.betAllin(game.getCurrentPlayer().getCurrentStack());
                    }
                }
                case RV_B -> {
                    if (rnd < 0.45) {
                        yield Decision.fold;
                    } else if (rnd < 0.5) {
                        yield Decision.raise(game.getUncalledBet() * 2);
                    } else if (rnd < 0.55) {
                        yield Decision.raise(game.getUncalledBet() * 3);
                    } else if (rnd < 0.6) {
                        yield Decision.raise(game.getUncalledBet() * 4);
                    } else if (rnd < 0.95) {
                        yield Decision.call(game.getUncalledBet());
                    } else {
                        yield Decision.raiseAllin(game.getCurrentPlayer().getCurrentStack());
                    }
                }
                case RV_C -> {
                    if (rnd < 0.25) {
                        yield Decision.bet(game.getCurrentPot() * 0.25);
                    } else if (rnd < 0.35) {
                        yield Decision.bet(game.getCurrentPot() * 0.5);
                    } else if (rnd < 0.45) {
                        yield Decision.bet(game.getCurrentPot() * 0.75);
                    } else if (rnd < 0.55) {
                        yield Decision.bet(game.getCurrentPot() * 1.0);
                    } else if (rnd < 0.65) {
                        yield Decision.bet(game.getCurrentPot() * 1.25);
                    } else if (rnd < 0.97) {
                        yield Decision.check;
                    } else {
                        yield Decision.betAllin(game.getCurrentPlayer().getCurrentStack());
                    }
                }
                case RV_CB -> {
                    if (rnd < 0.3) {
                        yield Decision.fold;
                    } else if (rnd < 0.4) {
                        yield Decision.raise(game.getUncalledBet() * 2);
                    } else if (rnd < 0.5) {
                        yield Decision.raise(game.getUncalledBet() * 3);
                    } else if (rnd < 0.6) {
                        yield Decision.raise(game.getUncalledBet() * 4);
                    } else if (rnd < 0.98) {
                        yield Decision.call(game.getUncalledBet());
                    } else {
                        yield Decision.raiseAllin(game.getCurrentPlayer().getCurrentStack());
                    }
                }
                case RV_CBR -> {
                    if (rnd < 0.35) {
                        yield Decision.fold;
                    } else if (rnd < 0.45) {
                        yield Decision.raise(game.getUncalledBet() * 2);
                    } else if (rnd < 0.55) {
                        yield Decision.raise(game.getUncalledBet() * 3);
                    } else if (rnd < 0.65) {
                        yield Decision.raise(game.getUncalledBet() * 4);
                    } else if (rnd < 0.98) {
                        yield Decision.call(game.getUncalledBet());
                    } else {
                        yield Decision.raiseAllin(game.getCurrentPlayer().getCurrentStack());
                    }
                }
                case RV_BR -> {
                    if (rnd < 0.6) {
                        yield Decision.fold;
                    } else if (rnd < 0.65) {
                        yield Decision.raise(game.getUncalledBet() * 2.5);
                    } else if (rnd < 0.7) {
                        yield Decision.raise(game.getUncalledBet() * 4);
                    } else if (rnd < 0.75) {
                        yield Decision.raise(game.getUncalledBet() * 4);
                    } else if (rnd < 0.98) {
                        yield Decision.call(game.getUncalledBet());
                    } else {
                        yield Decision.raiseAllin(game.getCurrentPlayer().getCurrentStack());
                    }
                }
                case RV_MR -> {
                    if (rnd < 0.4) {
                        yield Decision.fold;
                    } else if (rnd < 0.55) {
                        yield Decision.call(game.getUncalledBet());
                    } else {
                        yield Decision.raiseAllin(game.getCurrentPlayer().getCurrentStack());
                    }
                }
                case RV_A -> {
                    if (rnd < 0.55) {
                        yield Decision.fold;
                    } else {
                        yield Decision.call(game.getUncalledBet());
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + gameState);
            };
        }
    },
    FRAUDO {

        public static final int PREFLOP_BEST_POINTS = 20;
        public static final int FLOP_BEST_POINTS = 36;
        public static final int TURN_BEST_POINTS = 40;

        private int getDistanceToBestScore(Street street, int currentPlayerPoints) {
            return switch (street) {
                case PF -> Math.abs(currentPlayerPoints - PREFLOP_BEST_POINTS);
                case FL -> Math.abs(currentPlayerPoints - FLOP_BEST_POINTS);
                case TN -> Math.abs(currentPlayerPoints - TURN_BEST_POINTS);
                case RV -> Math.abs(currentPlayerPoints - PointsEvaluator.GAME_GOAL_POINTS);
            };
        }

        @Override
        public Decision makeDecision(Game game) {
            Street street = game.getStreet();
            Player currentPlayer = game.getCurrentPlayer();
            Player opponent = game.getCurrentPlayerOpponent();
            int currentPlayerPoints = currentPlayer.getPoints(street);
            int opponentPoints = opponent.getPoints(street);
            boolean hasAce = currentPlayer.getPreflopHand().contains(Card.ACE);
            boolean opponentHasAce = false;//opponent.getPreflopHand().contains(Card.ACE);
            int dist = getDistanceToBestScore(street, currentPlayerPoints);
            int oppPlayerDist = getDistanceToBestScore(street, opponentPoints);
            GameState gameState = game.getState();
            boolean hasBetterScore = dist < oppPlayerDist;
            boolean boardContainsAce = game.getBoardCards().contains(Card.ACE);
            return switch (gameState) {
                case PF_FIRST -> {
                    if (hasAce && dist <= 4) {
                        yield Decision.raise(game.getBbAmount() * 2);
                    } else {
                        yield Decision.fold;
                    }
                }
                case PF_A,
                        PF_R,
                        PF_RR,
                        PF_RRR -> {
                    if (hasAce && dist <= 4) {
                        yield Decision.call(game.getUncalledBet());
                    } else {
                        yield Decision.fold;
                    }
                }
                case FL_FIRST, FL_C -> {
                    if (dist <= 3 && boardContainsAce) {
                        yield Decision.bet(game.getCurrentPot() * 0.25);
                    } else {
                        yield Decision.check;
                    }
                }
                case TN_FIRST, TN_C -> {
                    if (dist <= 3 && boardContainsAce) {
                        yield Decision.bet(game.getCurrentPot() * 0.66);
                    } else {
                        yield Decision.check;
                    }
                }
                case FL_A, TN_A, FL_B, FL_CB, FL_BR, FL_MR, TN_B, TN_CB, TN_BR, TN_MR, FL_CBR, TN_CBR -> {
                    if (dist <= 3 && boardContainsAce) {
                        yield Decision.call(game.getUncalledBet());
                    } else {
                        yield Decision.fold;
                    }
                }
                case RV_FIRST, RV_C -> {
                    if (hasBetterScore && boardContainsAce) {
                        yield Decision.bet(game.getCurrentPot() * 0.75);
                    } else {
                        yield Decision.check;
                    }
                }
                case RV_A, RV_B, RV_CB, RV_BR, RV_MR, RV_CBR -> {
                    if (hasBetterScore && boardContainsAce) {
                        yield Decision.call(game.getUncalledBet());
                    } else {
                        yield Decision.fold;
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + gameState);
            };
        }
    },
    SMARTY {

        public static final int PREFLOP_BEST_POINTS = 20;
        public static final int FLOP_BEST_POINTS = 36;
        public static final int TURN_BEST_POINTS = 40;

        private int getDistanceToBestScore(Street street, int currentPlayerPoints) {
            return switch (street) {
                case PF -> Math.abs(currentPlayerPoints - PREFLOP_BEST_POINTS);
                case FL -> Math.abs(currentPlayerPoints - FLOP_BEST_POINTS);
                case TN -> Math.abs(currentPlayerPoints - TURN_BEST_POINTS);
                case RV -> Math.abs(currentPlayerPoints - PointsEvaluator.GAME_GOAL_POINTS);
            };
        }

        @Override
        public Decision makeDecision(Game game) {
            Street street = game.getStreet();
            Player currentPlayer = game.getCurrentPlayer();
            int currentPlayerPoints = currentPlayer.getPoints(street);
            boolean hasAce = currentPlayer.getPreflopHand().contains(Card.ACE);
            int dist = getDistanceToBestScore(street, currentPlayerPoints);
            GameState gameState = game.getState();
            boolean boardContainsAce = game.getBoardCards().contains(Card.ACE);
            return switch (gameState) {
                case PF_FIRST -> {
                    if (hasAce || dist <= 4) {
                        yield Decision.raise(game.getBbAmount() * 2);
                    } else {
                        yield Decision.fold;
                    }
                }
                case PF_A,
                        PF_R,
                        PF_RR,
                        PF_RRR -> {
                    if (hasAce || dist <= 4) {
                        yield Decision.call(game.getUncalledBet());
                    } else {
                        yield Decision.fold;
                    }
                }
                case FL_FIRST, FL_C -> {
                    if (dist <= 3 && boardContainsAce) {
                        yield Decision.bet(game.getCurrentPot() * 0.25);
                    } else {
                        yield Decision.check;
                    }
                }
                case TN_FIRST, TN_C -> {
                    if (dist <= 3 && boardContainsAce) {
                        yield Decision.bet(game.getCurrentPot() * 0.66);
                    } else {
                        yield Decision.check;
                    }
                }
                case FL_A, TN_A, FL_B, FL_CB, FL_BR, FL_MR, TN_B, TN_CB, TN_BR, TN_MR, FL_CBR, TN_CBR -> {
                    if (dist <= 3 && boardContainsAce) {
                        yield Decision.call(game.getUncalledBet());
                    } else {
                        yield Decision.fold;
                    }
                }
                case RV_FIRST, RV_C -> {
                    if (dist <= 3 && boardContainsAce) {
                        yield Decision.bet(game.getCurrentPot() * 0.75);
                    } else {
                        yield Decision.check;
                    }
                }
                case RV_A, RV_B, RV_CB, RV_BR, RV_MR, RV_CBR -> {
                    if (dist <= 3 && boardContainsAce) {
                        yield Decision.call(game.getUncalledBet());
                    } else {
                        yield Decision.fold;
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + gameState);
            };
        }
    }

}
