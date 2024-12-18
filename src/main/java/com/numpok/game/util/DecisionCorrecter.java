package com.numpok.game.util;

import com.numpok.game.common.Decision;
import com.numpok.game.engine.Game;
import com.numpok.game.engine.Player;

public enum DecisionCorrecter {
    ;

    public static Decision correct(Game game, Decision decision) {
        return switch (decision.decisionType()) {
            case CALL -> {
                Player currentPlayer = game.getCurrentPlayer();
                double currentStack = currentPlayer.getCurrentStack();
                yield currentStack < game.getUncalledBet() ? Decision.callAllin(currentPlayer.getCurrentStack()) : decision;
            }
            case RAISE -> {
                double amount = decision.amount();
                Player opponent = game.getCurrentPlayerOpponent();
                double opponentStack = opponent.getCurrentStack();
                yield opponentStack < amount ? Decision.raise(opponentStack) : decision;
            }
            case BET -> {
                double amount = decision.amount();
                Player opponent = game.getCurrentPlayerOpponent();
                double opponentStack = opponent.getCurrentStack();
                yield opponentStack < amount ? Decision.bet(opponentStack) : decision;
            }
            default -> decision;
        };
    }
}
