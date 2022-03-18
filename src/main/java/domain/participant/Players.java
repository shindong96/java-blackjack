package domain.participant;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import domain.card.Card;
import domain.result.EarningRate;

public class Players {
	private final List<Player> players;

	public Players(List<Player> players) {
		this.players = players;
	}

	public List<String> showNames() {
		return new ArrayList<>(players.stream().map(Player::showName).collect(Collectors.toList()));
	}

	public void addCard(int idx, Card card) {
		players.get(idx).addCard(card);
	}

	public boolean checkAllBust() {
		long count = players.stream()
			.filter(Player::isBust)
			.count();
		return count == players.size();
	}

	public boolean checkBust(int idx) {
		return players.get(idx).isBust();
	}

	public boolean checkMaxScore(int idx) {
		return players.get(idx).isMaxScore();
	}

	public LinkedHashMap<Participant, EarningRate> getResult(Dealer other) {
		LinkedHashMap<Participant, EarningRate> map = new LinkedHashMap<>();
		players.stream()
			.forEach(player -> map.put(player, player.getResult(other)));
		return map;
	}

	public int getSize() {
		return players.size();
	}

	public String showName(int idx) {
		return players.get(idx).showName();
	}

	public List<String> showHand(int idx) {
		return players.get(idx).showHand();
	}

	public int showScore(int idx) {
		return players.get(idx).getScore();
	}
}
