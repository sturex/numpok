package com.numpok.service;

import com.numpok.game.engine.Game;
import com.numpok.controller.graphql.SelfPlayPropertiesInput;
import com.numpok.controller.graphql.SolutionAliasInput;

import java.util.List;
import java.util.Set;

public interface SelfPlayService {
    List<Game> selfplay(Set<SolutionAliasInput> solutionAliasInputs, SelfPlayPropertiesInput selfPlayPropertiesInput);
}
