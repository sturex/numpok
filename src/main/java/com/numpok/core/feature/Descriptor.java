package com.numpok.core.feature;

import java.util.Objects;

public class Descriptor<T> {
    private final Enum<?> name;
    private final T value;

    public Descriptor(Enum<?> name, T value) {
        Objects.requireNonNull(value);
        this.name = name;
        this.value = value;
    }

    public Enum<?> name() {
        return name;
    }

    public T value() {
        return value;
    }

    @Override
    public String toString() {
        return name.name() + ": " + value;
    }
}
