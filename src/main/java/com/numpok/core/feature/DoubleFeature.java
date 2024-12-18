package com.numpok.core.feature;


import com.numpok.core.fsm.Stateful;

import java.util.function.Function;

public class DoubleFeature<S extends Stateful<? extends FeaturedState<S>>> extends Feature<Double, S> {
    public DoubleFeature(Enum<?> name, Function<S, Double> func) {
        super(name, s -> new Descriptor<>(name, func.apply(s)));
    }
}