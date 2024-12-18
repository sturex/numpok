package com.numpok.core.fsm;

public interface Action<S extends Stateful<?>, C> {
    void apply(S stateful, C context);
}
