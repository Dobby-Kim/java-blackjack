package model.participant;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import model.card.Card;
import model.card.Emblem;
import model.card.Number;
import model.dto.FaceUpResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EntrantTest {
    Entrant entrant;

    @BeforeEach
    void setUp() {
        // Given
        Names names = new Names(List.of("프람"));
        entrant = new Entrant(names);

        // When
        entrant.hitDealer(Card.from(Number.ACE, Emblem.SPADE));
        entrant.hitDealer(Card.from(Number.TWO, Emblem.SPADE));
        entrant.hitDealer(Card.from(Number.THREE, Emblem.SPADE));

        entrant.getPlayers()
                .peek()
                .hitCard(Card.from(Number.ACE, Emblem.HEART));
        entrant.getPlayers()
                .peek()
                .hitCard(Card.from(Number.TWO, Emblem.HEART));
        entrant.getPlayers()
                .peek()
                .hitCard(Card.from(Number.THREE, Emblem.HEART));

    }

    @Test
    @DisplayName("딜러의 정보(이름, 카드 패, 핸드)를 포함하는 데이터 전송 객체를 반환한다. ")
    void getDealerFaceUpResult_ShouldReturnDealerCardInfo_WhenCalled() {

        // Then
        FaceUpResult result = entrant.getDealerFaceUpResult();
        assertAll(() -> {
            assertEquals("딜러", result.getPartipantNameAsString());
            assertEquals(List.of("A스페이드", "2스페이드", "3스페이드"), result.getCardsAsStrings());
            assertEquals(16, result.hand());
        });
    }

    @Test
    @DisplayName("딜러의 정보(이름, 카드 패, 핸드)를 포함하는 데이터 전송 객체를 반환한다. ")
    void getPlayerFaceUpResult_ShouldReturnPlayerCardInfo_WhenCalled() {

        // Then
        List<FaceUpResult> result = entrant.getPlayerFaceUpResults();
        assertAll(() -> {
            assertEquals("프람", result.get(0)
                    .getPartipantNameAsString());
            assertEquals(List.of("A하트", "2하트", "3하트"), result.get(0)
                    .getCardsAsStrings());
            assertEquals(16, result.get(0)
                    .hand());
        });
    }

}
