package blackjack.domain.state;

public class Stay extends Finished {
    public Stay(final Cards cards) {
        super(cards);
    }

    @Override
    public double earningRate() {
        return 1;
    }
}