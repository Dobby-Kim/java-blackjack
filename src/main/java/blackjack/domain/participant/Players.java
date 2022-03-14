package blackjack.domain.participant;

import blackjack.domain.card.Card;
import blackjack.domain.game.GameOutcome;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Players {

    private final List<Player> values;
    private int currentTurnIndex;

    public Players(final List<Player> values) {
        validateDuplicationPlayers(values);
        this.values = values;
    }

    private void validateDuplicationPlayers(final List<Player> players) {
        if (calculateDistinctCount(players) != players.size()) {
            throw new IllegalArgumentException("이름 간에 중복이 있으면 안됩니다.");
        }
    }

    private int calculateDistinctCount(final List<Player> players) {
        return (int) players.stream()
                .map(Player::getName)
                .distinct()
                .count();
    }

    public List<Player> getInitPlayers() {
        return values;
    }

    public void turnToNextPlayer() {
        validateAllTurnEnd();
        currentTurnPlayer().stay();
        currentTurnIndex++;
    }

    private void validateAllTurnEnd() {
        if (isAllTurnEnd()) {
            throw new IllegalStateException("모든 턴이 종료되었습니다.");
        }
    }

    public boolean isAllTurnEnd() {
        return values.size() <= currentTurnIndex;
    }

    public Player drawCurrentPlayer(final Card card) {
        final Player currentPlayer = currentTurnPlayer();
        currentPlayer.draw(card);
        checkCanTurnNext(currentPlayer);
        return currentPlayer;
    }

    private Player currentTurnPlayer() {
        validateAllTurnEnd();
        return values.get(currentTurnIndex);
    }

    private void checkCanTurnNext(final Player currentPlayer) {
        if (!currentPlayer.canDraw()) {
            currentTurnIndex++;
        }
    }

    public Player getCurrentTurnPlayerInfo() {
        return currentTurnPlayer();
    }

    public Map<String, GameOutcome> calculateAllResults(final Dealer dealer) {
        return values.stream()
                .collect(Collectors.toUnmodifiableMap(Player::getName, dealer::judgeOutcomeOfPlayer));
    }
}