package domain.participant;

import domain.participant.info.Betting;
import domain.participant.info.Hand;
import domain.participant.info.Name;

public class Dealer extends Participant {

	private static final String DEALER_NAME = "딜러";
	private static final int STANDARD_OF_ENOUGH_CARD = 16;

	public Dealer(Hand hand) {
		super(new Name(DEALER_NAME), hand, new Betting());
	}

	public boolean isEnoughCard() {
		return getScore() > STANDARD_OF_ENOUGH_CARD;
	}
}
