package com.numpok.game.features;

import com.numpok.core.feature.IntFeature;
import com.numpok.game.engine.Game;
import com.numpok.core.feature.DoubleFeature;

public enum Features {
    ;
    public final static DoubleFeature<Game> totalPotSizeBb = new DoubleFeature<>(FeatureName.TOTAL_POT_SIZE_BB, Game::getCurrentPot);
    public final static IntFeature<Game> preflopPoints = new IntFeature<>(FeatureName.PREFLOP_POINTS, game -> game.getCurrentPlayer().getPreflopHand().points());
    public final static IntFeature<Game> flopPoints = new IntFeature<>(FeatureName.FLOP_POINTS, game -> game.getCurrentPlayer().getFlopHand().points());
    public final static IntFeature<Game> turnPoints = new IntFeature<>(FeatureName.TURN_POINTS, game -> game.getCurrentPlayer().getTurnHand().points());
    public final static IntFeature<Game> riverPoints = new IntFeature<>(FeatureName.RIVER_POINTS, game -> game.getCurrentPlayer().getRiverHand().points());
}
