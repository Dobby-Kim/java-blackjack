package model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RandomCardShuffleMachine implements CardShuffleMachine {

    public Queue<Card> shuffleCardDeck() {
        List<Card> allCards = generateAllCards();
        Collections.shuffle(allCards);
        return new LinkedList<>(allCards);
    }
}