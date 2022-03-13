package domain.result;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import domain.card.Card;
import domain.card.Rank;
import domain.card.Suit;
import domain.participant.Dealer;
import domain.participant.Name;
import domain.participant.Player;
import domain.participant.Players;

public class ResultTest {

	Card card_A = new Card(Rank.RANK_ACE, Suit.CLOVER);
	Card card_2 = new Card(Rank.RANK_TWO, Suit.CLOVER);
	Card card_Q = new Card(Rank.RANK_QUEEN, Suit.CLOVER);
	Card card_K = new Card(Rank.RANK_KNIGHT, Suit.CLOVER);
	Card card_6 = new Card(Rank.RANK_SIX, Suit.CLOVER);
	Card card_9 = new Card(Rank.RANK_NINE, Suit.CLOVER);
	List<Card> cards_20 = new ArrayList<>(Arrays.asList(card_A, card_9));
	List<Card> cards_15 = new ArrayList<>(Arrays.asList(card_9, card_6));
	List<Card> cards_BURST = new ArrayList<>(Arrays.asList(card_K, card_Q, card_2));
	List<Card> cards_17 = new ArrayList<>(Arrays.asList(card_A, card_6));
	Players players = new Players(
		Arrays.asList(new Player(new Name("pobi"), cards_20), new Player(new Name("jason"), cards_15),
			new Player(new Name("woni"), cards_BURST), new Player(new Name("gugu"), cards_17)));
	Dealer dealer_17 = new Dealer(List.of(card_A, card_6));
	Dealer dealer_BURST = new Dealer(List.of(card_K, card_Q, card_2));

	Result result = new Result(players.getResultAtFinal(dealer_17));
	Result result_dealer_burst = new Result(players.getResultAtFinal(dealer_BURST));

	@Test
	@DisplayName("딜러도 safe, 플레이어 safe, 플레이어 승")
	void getVersusOfPlayer_Pobi() {
		assertThat(result.getResultOfPlayer(new Name("pobi")).getResult()).isEqualTo("승");
	}

	@Test
	@DisplayName("딜러도 safe, 플레이어 safe, 무승부")
	void getVersusOfPlayer_Gugu() {
		assertThat(result.getResultOfPlayer(new Name("gugu")).getResult()).isEqualTo("무");
	}

	@Test
	@DisplayName("딜러도 safe, 플레이어 safe, 플레이어 패")
	void getVersusOfPlayer_Jason() {
		assertThat(result.getResultOfPlayer(new Name("jason")).getResult()).isEqualTo("패");
	}

	@Test
	@DisplayName("딜러도 safe, 플레이어 burst, 플레이어 패")
	void getVersusOfPlayer_Woni() {
		assertThat(result.getResultOfPlayer(new Name("woni")).getResult()).isEqualTo("패");
	}

	@Test
	@DisplayName("딜러도 burst, 플레이어 safe, 플레이어 승")
	void getVersusOfPlayer_Pobi2() {
		assertThat(result_dealer_burst.getResultOfPlayer(new Name("pobi")).getResult()).isEqualTo("승");
	}

	@Test
	@DisplayName("딜러도 burst, 플레이어 burst, 플레이어 패")
	void getVersusOfPlayer_Woni2() {
		assertThat(result_dealer_burst.getResultOfPlayer(new Name("woni")).getResult()).isEqualTo("패");
	}

	@Test
	@DisplayName("딜러 결과 확인")
	void getDealerResult() {
		assertThat(Arrays.asList(result.getDealerWinCount(), result.getDealerDrawCount(),
			result.getDealerLoseCount()))
			.isEqualTo(Arrays.asList(2, 1, 1));
	}
}
