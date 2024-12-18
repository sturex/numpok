package com.numpok.game.common;

import com.numpok.game.enums.DecisionType;

public record Decision(DecisionType decisionType, double amount) {

    public static Decision fold = new Decision(DecisionType.FOLD, 0);
    public static Decision check = new Decision(DecisionType.CHECK, 0);

    public static Decision call(double callAmount) {
        return new Decision(DecisionType.CALL, callAmount);
    }

    public static Decision raise(double amount) {
        return new Decision(DecisionType.RAISE, amount);
    }

    public static Decision bet(double amount) {
        return new Decision(DecisionType.BET, amount);
    }

    public static Decision betAllin(double stack) {
        return new Decision(DecisionType.BET_ALLIN, stack);
    }

    public static Decision raiseAllin(double stack) {
        return new Decision(DecisionType.RAISE_ALLIN, stack);
    }

    public static Decision callAllin(double stack) {
        return new Decision(DecisionType.CALL_ALLIN, stack);
    }

    public static Decision sb(double bbAmount) {
        return new Decision(DecisionType.SB, bbAmount / 2);
    }

    public static Decision bb(double bbAmount) {
        return new Decision(DecisionType.BB, bbAmount);
    }

    @Override
    public String toString() {
        return decisionType != DecisionType.FOLD && decisionType != DecisionType.CHECK ? decisionType + "(" + amount + ")" : decisionType.toString();
    }
}
