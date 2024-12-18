package com.numpok.service;

import com.numpok.game.common.Decision;
import com.numpok.game.ai.DecisionMakingSystem;
import com.numpok.game.ai.DumbBots;
import com.numpok.game.engine.Game;
import com.numpok.game.ai.DecisionGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DecisionMakingServiceImpl implements DecisionMakingService {

    private final Map<String, DecisionMakingSystem> solutionPool;

    @Autowired
    public DecisionMakingServiceImpl() {
        solutionPool = Arrays.stream(DumbBots.values()).collect(Collectors.toMap(Enum::name, Function.identity()));
    }

    @Override
    public Decision makeDecision(String solutionName, Game game) {
        return Optional.ofNullable(solutionPool.get(solutionName))
                .map(d -> d.makeDecision(game))
                .orElseThrow(() -> new IllegalArgumentException("Decision Making System with name=" + solutionName + " is not found in solution pool. Pool contains: " + String.join(", ", solutionPool.keySet())));
    }

    @Override
    public List<DecisionGame> makeDecisions(String solutionName, List<Game> games) {
        return Optional.ofNullable(solutionPool.get(solutionName))
                .map(d -> d.makeDecisions(games))
                .orElseThrow(() -> new IllegalArgumentException("Decision Making System with name=" + solutionName + " is not found in solution pool. Pool contains: " + String.join(", ", solutionPool.keySet())));
    }
}
