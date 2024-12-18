package com.numpok.controller;

import com.numpok.game.calc.SummaryStatistics;
import com.numpok.game.engine.Game;
import com.numpok.core.util.ExceptionHelper;
import com.numpok.game.util.StatisticsCalculator;
import com.numpok.service.SelfPlayService;
import com.numpok.controller.graphql.SelfPlayPropertiesInput;
import com.numpok.controller.graphql.SolutionAliasInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class GraphQlController {

    private final SelfPlayService selfPlayService;

    @Autowired
    public GraphQlController(SelfPlayService selfPlayService) {
        this.selfPlayService = selfPlayService;
    }

    @SchemaMapping(typeName = "Query", field = "selfPlay")
    public int selfPlay(@Argument List<SolutionAliasInput> solutionAliasInputs,
                        @Argument SelfPlayPropertiesInput selfPlayPropertiesInput
    ) {
        ExceptionHelper.throwIf(solutionAliasInputs.size() < 2, "Provide at least two players for selfplay.");
        ExceptionHelper.throwIf(selfPlayPropertiesInput.minInitialStack() < selfPlayPropertiesInput.bbAmount(), "Min initial stack must be greater than bb amount.");
        ExceptionHelper.throwIf(selfPlayPropertiesInput.maxInitialStack() < selfPlayPropertiesInput.bbAmount(), "Max initial stack must be greater than bb amount.");
        List<Game> games = selfPlayService.selfplay(new HashSet<>(solutionAliasInputs), selfPlayPropertiesInput);

        SummaryStatistics summaryStatistics = StatisticsCalculator.calculateSummaryStatistics(games);
        Map<String, List<Double>> map = StatisticsCalculator.calculateProfitChartSeriesMap(games);

        return 0;
    }


}
