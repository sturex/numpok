package com.numpok.game.engine;

import com.numpok.game.hand.PreflopHand;

public record PlayerInitialState(double stack, PreflopHand preflopHand, String playerName) {
}
