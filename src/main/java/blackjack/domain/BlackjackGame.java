package blackjack.domain;

import blackjack.domain.card.Deck;
import blackjack.domain.user.Dealer;
import blackjack.domain.user.Status;
import blackjack.domain.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BlackjackGame {
    private final Deck deck = Deck.createDeck();
    //todo : 일급컬렉션 생성
    private final List<User> users;
    private final Dealer dealer = new Dealer();

    private BlackjackGame(List<User> users) {
        this.users = new ArrayList<>(users);
    }

    public static BlackjackGame createAndFirstDraw(List<User> users) {
        BlackjackGame blackjackGame = new BlackjackGame(users);
        blackjackGame.init();
        return blackjackGame;
    }

    private void init() {
        dealer.firstDraw(deck.draw(), deck.draw());
        users.forEach(user -> user.firstDraw(deck.draw(), deck.draw()));
    }

    public int getDealerHandSize() {
        return dealer.handSize();
    }

    public Optional<User> findFirstUserByStatus(Status status){
        return users.stream()
                .filter(user -> user.isSameStatus(status))
                .findFirst();
    }
}
