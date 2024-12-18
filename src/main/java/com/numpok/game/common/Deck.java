package com.numpok.game.common;

import com.numpok.game.enums.Card;

import java.util.*;
import java.util.stream.Collectors;

public class Deck {
    private final List<Card> cards = Arrays.stream(Card.values()).collect(Collectors.toList());
    private int idx = 0;

    private Deck() {
    }

    public static Deck shuffled() {
        Deck deck = new Deck();
        Collections.shuffle(deck.cards);
        return deck;
    }

    public Card pick() {
        if (idx < cards.size()) {
            return cards.get(++idx);
        } else {
            throw new IllegalStateException("Deck is empty");
        }
    }
}



