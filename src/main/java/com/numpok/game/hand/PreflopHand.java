package com.numpok.game.hand;

import com.numpok.game.enums.Card;

import java.util.EnumSet;

public class PreflopHand extends Hand {

    private PreflopHand(Card card1, Card card2) {
        super(EnumSet.of(card1, card2));
    }

    public static PreflopHand of(Card card1, Card card2) {
        return new PreflopHand(card1, card2);
    }

    public FlopHand addFlopCards(EnumSet<Card> cards) {
        EnumSet<Card> flCards = EnumSet.copyOf(this.cards());
        flCards.addAll(cards);
        return new FlopHand(flCards);
    }

}
