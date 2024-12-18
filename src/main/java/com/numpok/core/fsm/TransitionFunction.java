package com.numpok.core.fsm;

public interface TransitionFunction<E extends Enum<E>, S extends Stateful<E>, C> {
    E apply(S stateful, int idx, C context);
}