package com.numpok.game.engine;

import com.numpok.core.fsm.Stateful;
import com.numpok.game.common.Decision;
import com.numpok.game.enums.Card;
import com.numpok.game.enums.DecisionType;
import com.numpok.game.enums.Position;
import com.numpok.game.enums.Street;
import com.numpok.game.hand.FlopHand;
import com.numpok.game.hand.PreflopHand;
import com.numpok.game.hand.RiverHand;
import com.numpok.game.hand.TurnHand;

import java.util.EnumSet;

public class Player implements Stateful<PlayerState> {
    private final Position position;
    private final String playerName;
    private final PreflopHand preflopHand;
    private FlopHand flopHand;
    private TurnHand turnHand;
    private PlayerState playerState = PlayerState.IN_GAME;
    private RiverHand riverHand;
    private final double initialStack;
    private double currentStack;
    private double totalInvested = 0;
    private double totalCollected = 0;
    public Player(Position position, String playerName, PreflopHand preflopHand, double stack) {
        this.position = position;
        this.playerName = playerName;
        this.preflopHand = preflopHand;
        this.initialStack = stack;
        this.currentStack = stack;
    }

    public double getTotalInvested() {
        return totalInvested;
    }

    void setTotalCollected(double amount) {
        totalCollected = amount;
    }

    public double getProfit() {
        return totalCollected - totalInvested;
    }

    public Position getPosition() {
        return position;
    }

    public int getPoints(Street street) {
        return switch (street) {
            case PF -> preflopHand.points();
            case FL -> flopHand.points();
            case TN -> turnHand.points();
            case RV -> riverHand.points();
        };
    }

    public FlopHand getFlopHand() {
        return flopHand;
    }

    public TurnHand getTurnHand() {
        return turnHand;
    }

    public RiverHand getRiverHand() {
        return riverHand;
    }

    public PreflopHand getPreflopHand() {
        return preflopHand;
    }

    public double getInitialStack() {
        return initialStack;
    }

    public double getCurrentStack() {
        return currentStack;
    }

    public String getPlayerName() {
        return playerName;
    }

    void accept(Decision decision) {
        double amount = decision.amount();
        currentStack -= amount;
        assert currentStack >= 0 : "currentStack=" + currentStack + ", expected currentStack >= 0";
        totalInvested += amount;
        DecisionType decisionType = decision.decisionType();
    }

    void acceptFlopCards(EnumSet<Card> cards) {
        flopHand = preflopHand.addFlopCards(cards);
    }

    void acceptTurnCard(Card card) {
        turnHand = flopHand.addTurnCard(card);
    }

    void acceptRiverCard(Card card) {
        riverHand = turnHand.addRiverCard(card);
    }

    @Override
    public void setState(PlayerState playerState) {
        this.playerState = playerState;
    }

    @Override
    public PlayerState getState() {
        return playerState;
    }

    @Override
    public String toString() {
        return playerName + ":" + position + ":" + preflopHand.toString() + " (" + initialStack + "->" + currentStack + ") - " + playerState + ", collected:" + totalCollected + ", invested:" + totalInvested + ", profit:" + getProfit();
    }
}
