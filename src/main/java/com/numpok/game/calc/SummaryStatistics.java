package com.numpok.game.calc;

import java.util.DoubleSummaryStatistics;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public record SummaryStatistics(Map<String, EnumMap<ProfitChartType, List<Double>>> profitChartSeries,
                                Map<String, Map<ProfitChartType, DoubleSummaryStatistics>> summary) {
}
