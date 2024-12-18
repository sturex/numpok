package com.numpok.controller.graphql;


public record SelfPlayPropertiesInput(
        double bbAmount,
        double maxInitialStack,
        double minInitialStack,
        int batchSize,
        int batchCount) {
}
