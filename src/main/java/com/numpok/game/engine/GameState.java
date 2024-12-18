package com.numpok.game.engine;

import com.numpok.core.feature.Feature;
import com.numpok.core.feature.FeaturedState;

import java.util.List;

public enum GameState implements FeaturedState<Game> {
    BB,
    END,
    FL_A,
    FL_A_COMPLETED,
    FL_B,
    FL_BR,
    FL_C,
    FL_CB,
    FL_CBR,
    FL_COMPLETED,
    FL_FIRST,
    FL_MR,
    GAME_COMPLETED,
    PF_A,
    PF_FIRST,
    PF_R,
    PF_RR,
    PF_RRR,
    PREFLOP_A_COMPLETED,
    PREFLOP_COMPLETED,
    RV_A,
    RV_B,
    RV_BR,
    RV_C,
    RV_CB,
    RV_CBR,
    RV_FIRST,
    RV_MR,
    SB,
    TN_A,
    TN_A_COMPLETED,
    TN_B,
    TN_BR,
    TN_C,
    TN_CB,
    TN_CBR,
    TN_COMPLETED,
    TN_FIRST,
    TN_MR;

    @Override
    public List<Feature<?, Game>> getFeatures() {
        throw new RuntimeException("Not implemented yet");
    }
}
