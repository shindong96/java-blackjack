package domain.participant;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import domain.card.Card;
import domain.card.Rank;
import domain.card.Suit;
import domain.result.WinOrLose;

public class PlayerTest {
	List<Card> handForPlayer;
	Player player;

	@BeforeEach
	void setUp() {
		handForPlayer = new ArrayList<>(
			List.of(new Card(Rank.RANK_EIGHT, Suit.CLOVER), new Card(Rank.RANK_ACE, Suit.CLOVER)));
		player = new Player(new Name("pobi"), handForPlayer);
	}

	@Test
	@DisplayName("블랙잭 발생 시 승패 판단")
	void compareAtBlackJack() {
		List<Card> handForDealer = new ArrayList<>(
			List.of(new Card(Rank.RANK_JACK, Suit.CLOVER), new Card(Rank.RANK_ACE, Suit.CLOVER)));
		Dealer dealer = new Dealer(handForDealer);
		assertThat(player.compareAtBlackJack(dealer)).isEqualTo(WinOrLose.LOSE);
	}

	@Test
	@DisplayName("최종 결과를 위한 승패 판단")
	void compareAtFinal() {
		List<Card> handForDealer = new ArrayList<>(
			List.of(new Card(Rank.RANK_JACK, Suit.CLOVER), new Card(Rank.RANK_ACE, Suit.CLOVER)));
		Dealer dealer = new Dealer(handForDealer);
		assertThat(player.compareAtFinal(dealer)).isEqualTo(WinOrLose.LOSE);
	}
	
}
