package blackjack.fixture;

import static blackjack.fixture.CardRepository.CLOVER2;
import static blackjack.fixture.CardRepository.CLOVER10;
import static blackjack.fixture.CardRepository.CLOVER3;
import static blackjack.fixture.CardRepository.CLOVER4;
import static blackjack.fixture.CardRepository.CLOVER5;
import static blackjack.fixture.CardRepository.CLOVER6;
import static blackjack.fixture.CardRepository.CLOVER7;
import static blackjack.fixture.CardRepository.CLOVER8;
import static blackjack.fixture.CardRepository.CLOVER_ACE;
import static blackjack.fixture.CardRepository.CLOVER_KING;

import blackjack.domain.card.Card;
import blackjack.domain.card.CardBundle;

public class CardBundleGenerator {

    public static CardBundle generateCardBundleOf(Card... cards) {
        if (cards.length < 2) {
            throw new IllegalArgumentException("최소 두 장의 카드를 입력하여 테스트해야 합니다.");
        }
        CardBundle cardBundle = CardBundle.of(cards[0], cards[1]);
        for (int i = 2; i < cards.length; i++) {
            cardBundle = cardBundle.addAndGenerate(cards[i]);
        }
        return cardBundle;
    }

    public static CardBundle getCardBundleOfTen() {
        return generateCardBundleOf(CLOVER4, CLOVER6);
    }

    public static CardBundle getCardBundleOfFifteen() {
        return generateCardBundleOf(CLOVER5, CLOVER10);
    }

    public static CardBundle getCardBundleOfSixteen() {
        return generateCardBundleOf(CLOVER6, CLOVER10);
    }

    public static CardBundle getCardBundleOfSeventeen() {
        return generateCardBundleOf(CLOVER7, CLOVER10);
    }

    public static CardBundle getCardBundleOfTwenty() {
        return generateCardBundleOf(CLOVER10, CLOVER_KING);
    }

    public static CardBundle getCardBundleOfNonBlackjackTwentyOne() {
        return generateCardBundleOf(CLOVER3, CLOVER8, CLOVER_KING);
    }

    public static CardBundle getCardBundleOfBlackjack() {
        return generateCardBundleOf(CLOVER_ACE, CLOVER_KING);
    }

    public static CardBundle getCardBundleOfBust() {
        return generateCardBundleOf(CLOVER2, CLOVER10, CLOVER_KING);
    }
}