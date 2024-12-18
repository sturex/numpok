package com.numpok.game.util;

import com.numpok.game.calc.ProfitChartType;
import com.numpok.game.calc.SummaryStatistics;
import com.numpok.game.engine.Game;
import com.numpok.game.engine.Player;
import com.numpok.game.engine.PlayerState;

import java.util.*;
import java.util.stream.Collectors;

public enum StatisticsCalculator {
    ;

    public static SummaryStatistics calculateSummaryStatistics(Collection<Game> games) {
        Map<String, EnumMap<ProfitChartType, List<Double>>> profitChartData = games.stream()
                .flatMap(game -> Arrays.stream(game.getPlayers()))
                .collect(Collectors.groupingBy(Player::getPlayerName,
                        Collectors.groupingBy(player -> sawShowdown(player) ? ProfitChartType.BY_SHOWDOWN : ProfitChartType.WITHOUT_SHOWDOWN,
                                () -> new EnumMap<>(ProfitChartType.class),
                                Collectors.mapping(Player::getProfit, Collectors.toList()))));
        Map<String, Map<ProfitChartType, DoubleSummaryStatistics>> summary = profitChartData.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> {
                    EnumMap<ProfitChartType, List<Double>> map = e.getValue();
                    return map.entrySet().stream()
                            .collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().stream().mapToDouble(Double::doubleValue).summaryStatistics()));
                }));
        return new SummaryStatistics(profitChartData, summary);
    }

    public static Map<String, List<Double>> calculateProfitChartSeriesMap(Collection<Game> games) {
        return games.stream()
                .flatMap(game -> Arrays.stream(game.getPlayers()))
                .collect(Collectors.groupingBy(Player::getPlayerName,
                        Collectors.mapping(Player::getProfit, Collectors.toList())));
    }

    private static boolean sawShowdown(Player player) {
        return player.getState() == PlayerState.IN_GAME;
    }
}
