package blackjack.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static blackjack.domain.Fixture.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PlayerTest {

    static Stream<Arguments> generateData() {
        return Stream.of(
                Arguments.of(CARDS_SCORE_20, true),
                Arguments.of(CARDS_SCORE_21, false)
        );
    }

    @ParameterizedTest(name = "{displayName}")
    @DisplayName("ACE를 1로 했을 때 카드 합이 21 미만일 경우 true, 그 이상인 경우 false를 반환한다.")
    @MethodSource("generateData")
    public void isAbleToReceiveCard(List<Card> inputCards, boolean result) {
        Cards cards = new Cards(inputCards);
        Player player = new Player("jason");
        player.receiveCards(cards);
        assertThat(player.isAbleToReceiveCard()).isEqualTo(result);
    }

    @Test
    @DisplayName("둘 다 21을 초과하지 않을 경우, 플레이어가 딜러보다 점가 높아야 이긴다.")
    public void judgeResult_PlayerAndDealerScoreUnder21_PlayerWin() {
        Player player = new Player("json");
        Dealer dealer = new Dealer();
        player.receiveCards(new Cards(CARDS_SCORE_20));
        dealer.receiveCards(new Cards(CARDS_SCORE_19));
        assertThat(player.judgeResult(dealer)).isEqualTo(Result.WIN);
    }

    @Test
    @DisplayName("둘 다 21을 초과하지 않을 경우, 딜러가 플레이어보다 점수가 높으면 플레이어가 패배한다.")
    public void judgeResult_PlayerAndDealerScoreUnder21_PlayerLose() {
        Player player = new Player("json");
        Dealer dealer = new Dealer();
        player.receiveCards(new Cards(CARDS_SCORE_19));
        dealer.receiveCards(new Cards(CARDS_SCORE_20));
        assertThat(player.judgeResult(dealer)).isEqualTo(Result.LOSE);
    }

    @Test
    @DisplayName("딜러가 21을 초과할 때 플레이어가 21을 초과하면 플레이어 패배")
    public void judgeResult_PlayerAndDealerScoreOver21() {
        Player player = new Player("json");
        Dealer dealer = new Dealer();
        player.receiveCards(new Cards(CARDS_SCORE_22));
        dealer.receiveCards(new Cards(CARDS_SCORE_22));
        assertThat(player.judgeResult(dealer)).isEqualTo(Result.LOSE);
    }

    @Test
    @DisplayName("딜러가 21을 초과할 때 플레이어가 21을 초과하지 않으면 플레이어 승")
    public void judgeResult_DealerScoreOver21() {
        Player player = new Player("json");
        Dealer dealer = new Dealer();
        player.receiveCards(new Cards(CARDS_SCORE_19));
        dealer.receiveCards(new Cards(CARDS_SCORE_22));
    }

    @Test
    @DisplayName("플레이어가 블랙잭이고, 딜러가 블랙잭이 아닐 경우에 게임 결과는 '블랙잭'")
    public void judgeResult_Blackjack() {
        Player player = new Player("json");
        Dealer dealer = new Dealer();
        player.receiveCards(new Cards(CARDS_SCORE_BLACKJACK));
        dealer.receiveCards(new Cards(CARDS_SCORE_22));
        assertThat(player.judgeResult(dealer)).isEqualTo(Result.BLACKJACK);
    }

    @Test
    @DisplayName("플레이어와 딜러 둘 다 블랙잭인 경우, 게임 결과는 '무승부'")
    public void judgeResult_BothOfThemBlackjack() {
        Player player = new Player("json");
        Dealer dealer = new Dealer();
        player.receiveCards(new Cards(CARDS_SCORE_BLACKJACK));
        dealer.receiveCards(new Cards(CARDS_SCORE_BLACKJACK));
        assertThat(player.judgeResult(dealer)).isEqualTo(Result.DRAW);
    }

    @Test
    @DisplayName("플레이어의 처음 두 장의 카드 합이 21일 경우, 수익이 베팅 금액의 1.5배이다.")
    public void calculateFinalBetProfit_WhenPlayerIsBlackjack() {
        Player player = new Player("json");
        Dealer dealer = new Dealer();
        player.initBetAmount(10000);
        player.receiveCards(new Cards(CARDS_SCORE_BLACKJACK));
        dealer.receiveCards(new Cards(CARDS_SCORE_21));
        System.out.println(player.judgeResult(dealer));
        assertThat(player.calculateFinalBetProfit(dealer)).isEqualTo(new BetAmount(5000));
    }

    @Test
    @DisplayName("플레이어와 딜러가 동시에 블랙잭인 경우, 수익은 0이다.")
    public void calculateFinalBetProfit_WhenPlayerAndDealerAreBlackJack() {
        Player player = new Player("json");
        Dealer dealer = new Dealer();
        player.initBetAmount(10000);
        player.receiveCards(new Cards(CARDS_SCORE_BLACKJACK));
        dealer.receiveCards(new Cards(CARDS_SCORE_BLACKJACK));
        System.out.println(player.judgeResult(dealer));
        assertThat(player.calculateFinalBetProfit(dealer)).isEqualTo(new BetAmount(0));
    }
}