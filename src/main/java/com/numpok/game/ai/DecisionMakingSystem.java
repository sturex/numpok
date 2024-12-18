package com.numpok.game.ai;

import com.numpok.game.common.Decision;
import com.numpok.game.engine.Game;

import java.util.List;

public interface DecisionMakingSystem {
    Decision makeDecision(Game game);

    default List<DecisionGame> makeDecisions(List<Game> games) {
        return games.stream().map(game -> new DecisionGame(game, makeDecision(game))).toList();
    }
}
