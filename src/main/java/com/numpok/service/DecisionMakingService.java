package com.numpok.service;

import com.numpok.game.common.Decision;
import com.numpok.game.engine.Game;
import com.numpok.game.ai.DecisionGame;

import java.util.List;

public interface DecisionMakingService {
    Decision makeDecision(String solutionName, Game game);

    List<DecisionGame> makeDecisions(String solutionName, List<Game> games);
}
