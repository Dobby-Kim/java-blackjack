package blackjack.controller;

import blackjack.domain.BlackJackGame;
import blackjack.dto.UserDto;
import blackjack.dto.UserProfitsDto;
import blackjack.dto.UsersDto;
import blackjack.view.InputView;
import blackjack.view.OutputView;

public final class BlackJackController {

    private final InputView inputView;
    private final OutputView outputView;

    public BlackJackController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        BlackJackGame game = BlackJackGame.fromPlayerNames(inputView.inputPlayersAndMoney());
        game.drawInitialCards(users -> outputView.printInitCards(UsersDto.fromInit(users)));
        if (game.isEnd()) {
            game.settleGameEarly(
                user -> outputView.printDealerBlackJack(UserDto.fromEvery(user)),
                users -> outputView.printAllUserCardsWithScore(UsersDto.fromEvery(users)),
                (dealer, playerProfits) -> outputView.printPlayerMoney(UserProfitsDto.of(dealer, playerProfits))
            );
            return;
        }
        runHitOrStayPhase(game);
    }

    private void runHitOrStayPhase(BlackJackGame game) {
        game.hitOrStayCardsPlayer(
            user -> () -> inputView.inputWhetherToDrawCard(UserDto.fromEvery(user)),
            user -> outputView.printUserCards(UserDto.fromEvery(user))
        );
        game.hitOrStayCardsDealer(user -> outputView.printDealerHit(UserDto.fromEvery(user)));
        end(game);
    }

    private void end(BlackJackGame game) {
        game.settleGame(
            users -> outputView.printAllUserCardsWithScore(UsersDto.fromEvery(users)),
            (dealer, playerProfits) -> outputView.printPlayerMoney(UserProfitsDto.of(dealer, playerProfits))
        );
    }
}