package controller;

import java.util.List;
import java.util.stream.Collectors;

import domain.card.Deck;
import domain.card.deckstrategy.GeneralGenerationDeckStrategy;
import domain.participant.Dealer;
import domain.participant.Name;
import domain.participant.Player;
import domain.participant.Players;
import view.InputView;
import view.OutputView;

public class MainController {

	public void run() {
		ParticipantInformationPrintController participantsInfoPrintController = new ParticipantInformationPrintController();
		Deck deck = Deck.from(new GeneralGenerationDeckStrategy());
		Dealer dealer = new Dealer(deck.generateInitCards());
		Players players = new Players(makePlayers(makeNames(), deck));
		participantsInfoPrintController.printInitHands(dealer, players);

		if (dealer.isBlackJack()) {
			participantsInfoPrintController.printBlackJackResult(dealer, players);
			return;
		}

		drawForPlayers(deck, players, participantsInfoPrintController);
		drawForDealer(deck, dealer, players);

		participantsInfoPrintController.printHandAndResult(dealer, players);
		participantsInfoPrintController.printFinalResult(dealer, players);
	}

	private List<Name> makeNames() {
		try {
			List<String> names = InputView.inputNames();
			return names.stream()
				.map(Name::new)
				.collect(Collectors.toList());
		} catch (IllegalArgumentException e) {
			OutputView.printErrorMessage(e.getMessage());
			return makeNames();
		}
	}

	private List<Player> makePlayers(List<Name> names, Deck deck) {
		List<Player> players = names.stream()
			.map(name -> new Player(name, deck.generateInitCards()))
			.collect(Collectors.toList());
		return players;
	}

	private void drawForPlayers(Deck deck, Players players, ParticipantInformationPrintController controller) {
		players.getNames().stream()
			.forEach(name -> askAndDrawForPlayer(deck, players, name, controller));
	}

	private void drawForDealer(Deck deck, Dealer dealer, Players players) {
		if (!players.checkAllBust()) {
			drawForDealer(deck, dealer);
		}
	}

	private void askAndDrawForPlayer(Deck deck, Players players, Name name,
		ParticipantInformationPrintController controller) {
		boolean isKeepDraw = !(players.checkMaxScoreByName(name));

		while (isKeepDraw && InputView.askDraw(name.getName())) {
			players.addCardByName(name, deck.draw());
			controller.printHand(players, name);
			isKeepDraw = checkMaxScoreOrBust(players, name);
		}
	}

	private boolean checkMaxScoreOrBust(Players players, Name name) {
		if (players.checkMaxScoreByName(name)) {
			OutputView.printMaxScoreMessage();
			return false;
		}

		if (players.checkBustByName(name)) {
			OutputView.printBustMessage();
			return false;
		}
		return true;
	}

	private void drawForDealer(Deck deck, Dealer dealer) {
		while (!dealer.isEnoughCard()) {
			OutputView.printDealerDrawMessage();
			dealer.addCard(deck.draw());
		}
	}
}
