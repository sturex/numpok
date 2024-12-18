package com.numpok.core.feature;


import com.numpok.core.fsm.Stateful;

import java.util.List;

public interface FeaturedState<S extends Stateful<? extends FeaturedState<S>>> {

    List<Feature<?, S>> getFeatures();
}
