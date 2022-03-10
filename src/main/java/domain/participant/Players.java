package domain.participant;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import domain.card.Card;
import domain.result.Versus;

public class Players {

	private final List<Player> players;

	public Players(List<Name> names, List<List<Card>> initCards) {
		this.players = IntStream.range(0, names.size())
			.mapToObj(i -> new Player(names.get(i), initCards.get(i)))
			.collect(Collectors.toList());
	}

	public void addCardByName(Name name, Card card) {
		players.stream().filter(player -> player.compareName(name)).forEach(player -> player.addCard(card));
	}

	public List<String> showHands() {
		return players.stream().map(Player::showHand).collect(Collectors.toList());
	}

	public String showHandByName(Name name) {
		return players.stream()
			.filter(player -> player.compareName(name))
			.map(Player::showHand).findFirst().orElseThrow();
	}

	public List<String> showHandsAndBestScores() {
		return players.stream().map(Player::showHandAndBestScore).collect(Collectors.toList());
	}

	public boolean isAllBust() {
		long count = players.stream()
			.filter(Player::isBust)
			.count();

		return count == players.size();
	}

	public boolean isBustByName(Name name) {
		return players.stream()
			.filter(player -> player.compareName(name))
			.map((Player::isBust)).findFirst().orElseThrow();
	}

	public boolean isBlackJackByName(Name name) {
		return players.stream()
			.filter(player -> player.compareName(name))
			.map((Player::isBlackJack)).findFirst().orElseThrow();
	}

	public boolean isExistBlackJack() {
		return players.stream().filter(Player::isBlackJack).count() != 0;
	}

	public Map<Name, Versus> initCompare(boolean isBlackJack) {
		Map<Name, Versus> map = new LinkedHashMap<>();
		players.stream().forEach(player -> map.put(player.name, player.initCompare(isBlackJack)));
		return map;
	}

	public Map<Name, Versus> finalCompare(Participant other) {
		Map<Name, Versus> map = new LinkedHashMap<>();
		players.stream().forEach(player -> map.put(player.name, player.finalCompare(other)));
		return map;
	}
}
