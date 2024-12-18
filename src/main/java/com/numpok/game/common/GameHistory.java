package com.numpok.game.common;

import com.numpok.game.enums.Card;
import com.numpok.game.enums.Position;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;

public record GameHistory(
        EnumMap<Position, PlayerInitialState> initialStateByPosition,
        List<Decision> preflopDecisions,
        List<Decision> flopDecisions,
        List<Decision> turnDecisions,
        List<Decision> riverDecisions,
        EnumSet<Card> flopCards,
        Card turnCard,
        Card riverCard,
        EnumMap<Position, Double> profitByPosition) {

    public static final GameHistory empty = new GameHistory(null, null, null, null, null, null, null, null, null);

    public static GameHistory flopCardsContext(EnumSet<Card> flopCards) {
        return new GameHistory(null, null, null, null, null, flopCards, null, null, null);
    }

    public static GameHistory preflopMoveContext(Decision decision) {
        return new GameHistory(null, List.of(decision), null, null, null, null, null, null, null);
    }

    public static GameHistory flopMoveContext(Decision decision) {
        return new GameHistory(null, null, List.of(decision), null, null, null, null, null, null);
    }

    public static GameHistory turnMoveContext(Decision decision) {
        return new GameHistory(null, null, null, List.of(decision), null, null, null, null, null);
    }

    public static GameHistory riverMoveContext(Decision decision) {
        return new GameHistory(null, null, null, null, List.of(decision), null, null, null, null);
    }

    public static GameHistory turnCardContext(Card card) {
        return new GameHistory(null, null, null, null, null, null, card, null, null);
    }

    public static GameHistory riverCardContext(Card card) {
        return new GameHistory(null, null, null, null, null, null, null, card, null);
    }
}
