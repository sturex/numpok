package com.numpok.game.engine;

import java.util.EnumSet;

public enum PlayerState {
    FOLD_BY_FLOP, FOLD_BY_PREFLOP, FOLD_BY_RIVER, FOLD_BY_TURN,
    IN_GAME, WIN_BY_FLOP, WIN_BY_PREFLOP, WIN_BY_RIVER,
    WIN_BY_TURN;

    public static final EnumSet<PlayerState> foldStates = EnumSet.of(FOLD_BY_PREFLOP, FOLD_BY_FLOP, FOLD_BY_TURN, FOLD_BY_RIVER);
    public static final EnumSet<PlayerState> winStates = EnumSet.of(WIN_BY_PREFLOP, WIN_BY_FLOP, WIN_BY_TURN, WIN_BY_RIVER);
}
