package com.numpok.game.hand;

import com.numpok.game.enums.Card;
import com.numpok.game.util.PointsEvaluator;

import java.util.EnumSet;
import java.util.Objects;
import java.util.stream.Collectors;

abstract class Hand {
    private final int points;
    private final String name;
    private final EnumSet<Card> cards;

    protected Hand(EnumSet<Card> cards) {
        this.cards = cards;
        points = PointsEvaluator.eval(this.cards);
        name = this.cards.stream().sorted().map(Card::asChar).map(Objects::toString).collect(Collectors.joining());
    }

    public int points() {
        return points;
    }

    public EnumSet<Card> cards() {
        return cards;
    }

    public String name() {
        return name;
    }

    public boolean contains(Card card) {
        return cards.contains(card);
    }

    @Override
    public String toString() {
        return name + "::" + points;
    }
}
