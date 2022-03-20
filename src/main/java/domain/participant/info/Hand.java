package domain.participant.info;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import domain.card.Card;

public class Hand implements Iterable<Card> {
	private final List<Card> hand;

	public Hand(List<Card> hand) {
		this.hand = hand;
	}

	public boolean hasAce() {
		return hand.stream().anyMatch(Card::isAce);
	}

	public void add(Card card) {
		hand.add(card);
	}

	public int size() {
		return hand.size();
	}

	public List<Card> getHand() {
		return new ArrayList<>(hand);
	}

	private class HandIterator implements Iterator<Card> {

		private int current = 0;

		@Override
		public boolean hasNext() {
			return current < hand.size();
		}

		@Override
		public Card next() {
			return hand.get(current++);
		}
	}

	@Override
	public Iterator<Card> iterator() {
		return new HandIterator();
	}

	@Override
	public void forEach(Consumer<? super Card> action) {
		Iterable.super.forEach(action);
	}
}
