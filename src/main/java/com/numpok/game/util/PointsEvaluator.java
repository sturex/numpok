package com.numpok.game.util;

import com.numpok.game.enums.Card;

import java.util.EnumMap;
import java.util.EnumSet;

public enum PointsEvaluator {
    ;

    private static final EnumMap<Card, Integer> map = new EnumMap<>(Card.class);
    public static final int GAME_GOAL_POINTS = 50;

    static {
        map.put(Card.TWO, 2);
        map.put(Card.THREE, 3);
        map.put(Card.FOUR, 4);
        map.put(Card.FIVE, 5);
        map.put(Card.SIX, 6);
        map.put(Card.SEVEN, 7);
        map.put(Card.EIGHT, 8);
        map.put(Card.NINE, 9);
        map.put(Card.TEN, 10);
        map.put(Card.JACK, 10);
        map.put(Card.QUEEN, 10);
        map.put(Card.KING, 10);
    }

    public static int eval(EnumSet<Card> cards) {
        if (cards.contains(Card.ACE)) {
            int sumWithoutAce = cards.stream().filter(card -> card != Card.ACE).mapToInt(map::get).sum();
            int aceOnePointSum = sumWithoutAce + 1;
            int aceElevenPointsSum = sumWithoutAce + 11;
            return Math.abs(aceElevenPointsSum - GAME_GOAL_POINTS) < Math.abs(aceOnePointSum - GAME_GOAL_POINTS) ? aceElevenPointsSum : aceOnePointSum;
        } else {
            return cards.stream().mapToInt(map::get).sum();
        }
    }
}
