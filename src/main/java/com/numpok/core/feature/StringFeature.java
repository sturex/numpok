package com.numpok.core.feature;


import com.numpok.core.fsm.Stateful;

import java.util.function.Function;

public class StringFeature<S extends Stateful<? extends FeaturedState<S>>> extends Feature<String, S> {
    public StringFeature(Enum<?> name, Function<S, String> func) {
        super(name, s -> new Descriptor<>(name, func.apply(s)));
    }
}
