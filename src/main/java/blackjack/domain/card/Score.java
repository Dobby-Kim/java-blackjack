package blackjack.domain.card;

import java.util.List;
import java.util.Objects;

public final class Score {

    public static final int BLACKJACK = 21;

    private static final int ACE_POINT_DIFFERENCE = 10;
    private static final int ZERO = 0;
    private static final Score ZERO_SCORE = new Score(ZERO);

    private final int score;

    public Score(int score) {
        validatePositive(score);
        this.score = score;
    }

    public static Score getZero() {
        return ZERO_SCORE;
    }

    private void validatePositive(int score) {
        if (score < ZERO) {
            throw new IllegalArgumentException(String.format("점수는 %d점 미만이 될 수 없습니다.", ZERO));
        }
    }

    public static Score addUpPointToScore(List<Card> cards) {
        int aceCount = countAce(cards);
        if (aceCount > ZERO) {
            return sumWithAce(aceCount, cards);
        }
        return new Score(
            cards.stream()
                .mapToInt(Card::getPoint)
                .sum()
        );
    }

    private static int countAce(List<Card> cards) {
        return Math.toIntExact(
            cards.stream()
                .filter(Card::isAce)
                .count()
        );
    }

    private static Score sumWithAce(int aceCount, List<Card> cards) {
        int sum = cards.stream()
            .mapToInt(Card::getPoint)
            .sum();
        return new Score(findOptimalScore(sum, aceCount));
    }

    private static int findOptimalScore(int sum, int aceCount) {
        if (aceCount == ZERO) {
            return sum;
        }

        if (sum + ACE_POINT_DIFFERENCE <= BLACKJACK) {
            return findOptimalScore(sum + ACE_POINT_DIFFERENCE, aceCount - 1);
        }

        return sum;
    }

    public boolean isBust() {
        return score > BLACKJACK;
    }

    public boolean isBlackJackScore() {
        return score == BLACKJACK;
    }

    public boolean isGreaterThan(Score other) {
        return this.score > other.score;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Score{" +
            "score=" + score +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Score score1 = (Score)o;
        return score == score1.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(score);
    }
}