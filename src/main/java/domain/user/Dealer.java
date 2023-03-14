package domain.user;

import domain.card.Card;
import domain.card.Score;
import domain.state.State;

import java.util.List;

public class Dealer {

    private static final Score hitScoreLimit = new Score(16);
    private static final int DEALER_HAND_SIZE_LIMIT = 2;

    private final Participant participant;

    public Dealer(final String dealerName) {
        this.participant = new Participant(dealerName);
    }

    public boolean canHit() {
        return participantScore().isLessThanOrEqual(hitScoreLimit);
    }

    private Score participantScore() {
        return getState().score();
    }

    public State hit(final Card card) {
        State state = participant.hit(card);
        if (state.isRunning() && state.handSize() > DEALER_HAND_SIZE_LIMIT) {
            participant.stay();
        }
        return getState();
    }

    public State stay() {
        return participant.stay();
    }

    public String name() {
        return participant.name();
    }

    public State getState() {
        return participant.getState();
    }

    public List<Card> cards() {
        return participant.cards();
    }

    public Score getScore() {
        return getState().score();
    }
}