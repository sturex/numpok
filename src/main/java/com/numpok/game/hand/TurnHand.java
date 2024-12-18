package com.numpok.game.hand;

import com.numpok.game.enums.Card;

import java.util.EnumSet;

public class TurnHand extends Hand {

    protected TurnHand(EnumSet<Card> cards) {
        super(cards);
    }

    public RiverHand addRiverCard(Card card) {
        EnumSet<Card> cards = this.cards();
        EnumSet<Card> rvCards = EnumSet.copyOf(cards);
        rvCards.add(card);
        return new RiverHand(rvCards);
    }
}
