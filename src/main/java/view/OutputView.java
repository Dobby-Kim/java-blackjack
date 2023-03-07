package view;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import controller.ParticipantDto;
import domain.GameOutcome;
import controller.ParticipantDtoWithScore;
import java.util.List;
import java.util.Map;

public class OutputView {
    private static final String NAME_CARD_DELIMITER = ":";

    private static final String CARD_DELIMITER = ", ";

    public void printParticipantsInitialCards(List<ParticipantDto> participantDtos) {
        printInitialState(participantDtos);
        printInitialCards(participantDtos);
    }

    private void printInitialState(List<ParticipantDto> participantDtos) {
        String initialState = participantDtos.stream()
                .map(ParticipantDto::name)
                .collect(joining(CARD_DELIMITER, "", "에게 2장을 나누었습니다."));
        System.out.println(initialState);
    }

    private void printInitialCards(List<ParticipantDto> participantDtos) {
        participantDtos.forEach(participantDto -> {
            if (participantDto.name().equals("딜러")) {
                printFirstCard(participantDto);
                return;
            }
            printAllCards(participantDto);
        });
    }

    private void printFirstCard(ParticipantDto participantDto) {
        System.out.println(generateCardsInfo(participantDto.name(), participantDto.cards().subList(0, 1)));
    }

    public void printAllCards(ParticipantDto participantDto) {
        System.out.println(generateCardsInfo(participantDto.name(),
                participantDto.cards()));
    }

    private String generateCardsInfo(String name, List<String> cards) {
        String cardsInfo = String.join(CARD_DELIMITER, cards);
        return String.format("%s%s%s", name, NAME_CARD_DELIMITER, cardsInfo);
    }

    public void printDealerHandOutInfo() {
        System.out.println("딜러는 16이하라 한장의 카드를 더 받았습니다.");
    }

    public void printParticipantsScore(List<ParticipantDtoWithScore> participantDtosWithScore) {
        for (ParticipantDtoWithScore participantDtoWithScore : participantDtosWithScore) {
            System.out.println(generateCardsAndScore(participantDtoWithScore));
        }
    }

    private String generateCardsAndScore(ParticipantDtoWithScore participantDtoWithScore) {
        String name = participantDtoWithScore.name();
        List<String> cards = participantDtoWithScore.cards();
        int score = participantDtoWithScore.score();
        return String.format("%s - 결과: %d", generateCardsInfo(name, cards), score);
    }

    public void printGameOutcomes(Map<String, GameOutcome> playerOutcomes) {
        System.out.println("## 최종 승패");
        Map<GameOutcome, Long> dealerOutcome = generateDealerOutcome(playerOutcomes);
        printDealerOutcome(dealerOutcome);
        playerOutcomes.forEach((name, outcome) -> System.out.println(name + ": " + outcome.value()));
    }

    private Map<GameOutcome, Long> generateDealerOutcome(Map<String, GameOutcome> playerOutcomes) {
        return playerOutcomes.values()
                .stream()
                .collect(groupingBy(GameOutcome::oppositeOutcome, counting()));
    }

    private void printDealerOutcome(Map<GameOutcome, Long> dealerOutcome) {
        System.out.print("딜러:");
        dealerOutcome.forEach((outcome, count) -> System.out.print(" " + count + outcome.value()));
        System.out.println();
    }
}
