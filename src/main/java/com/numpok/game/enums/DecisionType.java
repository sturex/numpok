package com.numpok.game.enums;

import java.util.EnumSet;

public enum DecisionType {
    SB,
    BB,
    FOLD,
    CALL,
    RAISE,
    BET,
    CHECK,
    BET_ALLIN {
        @Override
        public DecisionType toNonAllin() {
            return BET;
        }

    },
    CALL_ALLIN {
        @Override
        public DecisionType toNonAllin() {
            return CALL;
        }

    },
    RAISE_ALLIN {
        @Override
        public DecisionType toNonAllin() {
            return RAISE;
        }

    };

    public DecisionType toNonAllin() {
        return this;
    }

    public static final EnumSet<DecisionType> allin = EnumSet.of(BET_ALLIN, RAISE_ALLIN, CALL_ALLIN);

}
