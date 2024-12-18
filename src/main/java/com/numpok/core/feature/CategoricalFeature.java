package com.numpok.core.feature;


import com.numpok.core.fsm.Stateful;

import java.util.EnumSet;
import java.util.function.Function;

public class CategoricalFeature<E extends Enum<E>, S extends Stateful<? extends FeaturedState<S>>> extends Feature<E, S> {

    private final EnumSet<E> values;

    public CategoricalFeature(Enum<?> name, Function<S, E> func, EnumSet<E> values) {
        super(name, s -> new Descriptor<>(name, func.apply(s)));
        this.values = values;
    }

    public EnumSet<E> values() {
        return values;
    }
}
