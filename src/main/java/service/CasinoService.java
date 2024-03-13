package service;

import static java.util.Collections.frequency;
import static model.casino.MatchResult.DRAW;
import static model.casino.MatchResult.LOSE;
import static model.casino.MatchResult.WIN;

import java.util.EnumMap;
import java.util.List;
import model.Choice;
import model.casino.CardDispenser;
import model.casino.CardShuffleMachine;
import model.casino.MatchResult;
import model.participant.Entrant;
import service.dto.DealerScoreResult;
import service.dto.FaceUpResult;
import service.dto.PlayerScoreResult;

public class CasinoService {
    private final Entrant entrant;
    private final CardDispenser cardDispenser;

    public CasinoService(Entrant entrant, CardShuffleMachine cardShuffleMachine) {
        this.entrant = entrant;
        this.cardDispenser = new CardDispenser(cardShuffleMachine);
    }

    public void initializeGame() {
        int playerSize = entrant.getPlayerSize();
        for (int i = 0; i < playerSize; i++) {
            entrant.hitAndMoveToNextPlayer(cardDispenser.dispenseCard());
            entrant.hitAndMoveToNextPlayer(cardDispenser.dispenseCard());
        }
        hitCardToDealer();
        hitCardToDealer();
    }

    public void distinctPlayerChoice(Choice choice) {
        if (choice.isHit()) {
            hitCardToPlayer();
            return;
        }
        entrant.turnOverPlayer();
    }

    private void hitCardToPlayer() {
        entrant.hitPlayer(cardDispenser.dispenseCard());
    }

    public void hitCardToDealer() {
        entrant.hitDealer(cardDispenser.dispenseCard());
    }

    public boolean hasAvailablePlayer() {
        return entrant.hasAvailablePlayer();
    }

    public boolean isDealerHitAllowed() {
        return entrant.canHitDealer();
    }

    public FaceUpResult getDealerFaceUpResult() {
        return entrant.getDealerFaceUpResult();
    }

    public List<FaceUpResult> getPlayerFaceUpResult() {
        return entrant.getPlayerFaceUpResults();
    }

    public FaceUpResult getNextPlayerFaceUpInfo() {
        return entrant.getNextAvailablePlayerName();
    }

    public List<PlayerScoreResult> calculatePlayerResults() {
        int dealerHand = entrant.getDealerFaceUpResult()
                .hand();
        return entrant.getPlayerFaceUpResults()
                .stream()
                .map(result -> new PlayerScoreResult(result.name(), MatchResult.of(dealerHand, result.hand())))
                .toList();
    }

    public DealerScoreResult calculateDealerResult() {
        EnumMap<MatchResult, Integer> dealerScoreBoard = new EnumMap<>(MatchResult.class);
        List<MatchResult> playerScores = calculatePlayerResults()
                .stream()
                .map(PlayerScoreResult::matchResult)
                .toList();
        dealerScoreBoard.put(WIN, frequency(playerScores, LOSE));
        dealerScoreBoard.put(DRAW, frequency(playerScores, DRAW));
        dealerScoreBoard.put(LOSE, frequency(playerScores, WIN));
        return new DealerScoreResult(dealerScoreBoard);
    }

}