package com.numpok.core.feature;


import com.numpok.core.fsm.Stateful;

import java.util.function.Function;

public class Feature<E, S extends Stateful<? extends FeaturedState<S>>> {

    private final Enum<?> name;
    private final Function<S, Descriptor<E>> descriptorsFunc;

    public Feature(Enum<?> name, Function<S, Descriptor<E>> descriptorsFunc) {
        this.name = name;
        this.descriptorsFunc = descriptorsFunc;
    }

    public Enum<?> name() {
        return name;
    }

    public Descriptor<E> descriptor(S stateful) {
        return descriptorsFunc.apply(stateful);
    }

    public E value(S stateful) {
        return descriptorsFunc.apply(stateful).value();
    }
}
