import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {
	static Dealer dealer, dealer2, dealer3;
	static Deck deck;
	static ArrayList<Card> straight = new ArrayList<Card>();
	static ArrayList<Card> pair = new ArrayList<Card>();
	static ArrayList<Card> three = new ArrayList<Card>();
	static ArrayList<Card> flush = new ArrayList<Card>();
	static ArrayList<Card> high = new ArrayList<Card>();
	static ArrayList<Card> strFlush = new ArrayList<Card>();
	static Card card;
	
	@BeforeAll
	static void setup() {
		dealer = new Dealer();
		dealer2 = new Dealer();
		dealer3 = new Dealer();
		deck = new Deck();
		
		card = new Card('C',2);
		straight.add(card);
		card = new Card('D',1);
		straight.add(card);
		card = new Card('H',3);
		straight.add(card);
		
		card = new Card('C',1);
		pair.add(card);
		card = new Card('D',2);
		pair.add(card);
		card = new Card('H',1);
		pair.add(card);
		
		card = new Card('C',3);
		three.add(card);
		card = new Card('D',3);
		three.add(card);
		card = new Card('H',3);
		three.add(card);
		
		card = new Card('C',1);
		flush.add(card);
		card = new Card('C',10);
		flush.add(card);
		card = new Card('C',3);
		flush.add(card);
		
		card = new Card('C',1);
		high.add(card);
		card = new Card('H',10);
		high.add(card);
		card = new Card('S',3);
		high.add(card);
		
		card = new Card('C',12);
		strFlush.add(card);
		card = new Card('C',11);
		strFlush.add(card);
		card = new Card('C',10);
		strFlush.add(card);
	}
	
	// DEALER TESTS
	@Test
	void testDealCardAmountConstruct() {
		assertEquals(52, dealer3.theDeck.size(), "NOT SAME AMOUNT!!!");
		assertEquals(52, dealer3.cardTotal, "NOT SAME AMOUNT!!!");
	}
	@Test
	void testDealHand() {
		assertEquals(52, dealer2.theDeck.size(), "NOT SAME AMOUNT!!!");
		assertEquals(52, dealer2.cardTotal, "NOT SAME AMOUNT!!!");
		ArrayList<Card> temp = dealer2.dealHand();
		assertEquals(49, dealer2.theDeck.size(), "NOT SAME AMOUNT!!!");
		assertEquals(49, dealer2.cardTotal, "NOT SAME AMOUNT!!!");
		assertEquals(3, temp.size(), "NOT SAME AMOUNT!!!");
	}
	@Test
	void testDealnewDeck() {
		assertEquals(52, dealer.theDeck.size(), "NOT SAME AMOUNT!!!");
		assertEquals(52, dealer.cardTotal, "NOT SAME AMOUNT!!!");
		ArrayList<Card> temp = dealer.dealHand();
		assertEquals(49, dealer.theDeck.size(), "NOT SAME AMOUNT!!!");
		assertEquals(49, dealer.cardTotal, "NOT SAME AMOUNT!!!");
		assertEquals(3, temp.size(), "NOT SAME AMOUNT!!!");
		temp = dealer.dealHand();
		assertEquals(46, dealer.theDeck.size(), "NOT SAME AMOUNT!!!");
		assertEquals(46, dealer.cardTotal, "NOT SAME AMOUNT!!!");
		assertEquals(3, temp.size(), "NOT SAME AMOUNT!!!");
		temp = dealer.dealHand();
		assertEquals(43, dealer.theDeck.size(), "NOT SAME AMOUNT!!!");
		assertEquals(43, dealer.cardTotal, "NOT SAME AMOUNT!!!");
		assertEquals(3, temp.size(), "NOT SAME AMOUNT!!!");
		temp = dealer.dealHand();
		assertEquals(40, dealer.theDeck.size(), "NOT SAME AMOUNT!!!");
		assertEquals(40, dealer.cardTotal, "NOT SAME AMOUNT!!!");
		assertEquals(3, temp.size(), "NOT SAME AMOUNT!!!");
		temp = dealer.dealHand();
		assertEquals(37, dealer.theDeck.size(), "NOT SAME AMOUNT!!!");
		assertEquals(37, dealer.cardTotal, "NOT SAME AMOUNT!!!");
		assertEquals(3, temp.size(), "NOT SAME AMOUNT!!!");
		temp = dealer.dealHand();
		assertEquals(34, dealer.theDeck.size(), "NOT SAME AMOUNT!!!");
		assertEquals(34, dealer.cardTotal, "NOT SAME AMOUNT!!!");
		assertEquals(3, temp.size(), "NOT SAME AMOUNT!!!");
		temp = dealer.dealHand();
		assertEquals(49, dealer.theDeck.size(), "NOT SAME AMOUNT!!!");
		assertEquals(49, dealer.cardTotal, "NOT SAME AMOUNT!!!");
		assertEquals(3, temp.size(), "NOT SAME AMOUNT!!!");
	}
	// DECK TESTS
	@Test
	void testDeckConstruct() {
		assertEquals(52, deck.size(), "NOT SAME AMOUNT!!!");
	}
	@Test
	void testnewDeck() {
		deck.newDeck();
		assertEquals(52, deck.size(), "NOT SAME AMOUNT!!!");
	}
	@Test
	void testDeckSuit() {
		assertEquals(52, deck.size(), "NOT SAME AMOUNT!!!");
		int heartCnt = 0;
		for (int i=0; i<52; i++) {
			Card card = deck.get(i);
			if(card.suit == 'H') {
				heartCnt++;
			}
		}
		assertEquals(13, heartCnt, "NOT SAME AMOUNT!!!");
	}
	@Test
	void testDeckValue() {
		assertEquals(52, deck.size(), "NOT SAME AMOUNT!!!");
		int kingCnt = 0;
		for (int i=0; i<52; i++) {
			Card card = deck.get(i);
			if(card.value == 13) {
				kingCnt++;
			}
		}
		assertEquals(4, kingCnt, "NOT SAME AMOUNT!!!");
	}
	@Test
	void testDeckValue2() {
		assertEquals(52, deck.size(), "NOT SAME AMOUNT!!!");
		int aceCnt = 0;
		for (int i=0; i<52; i++) {
			Card card = deck.get(i);
			if(card.value == 1) {
				aceCnt++;
			}
		}
		assertEquals(4, aceCnt, "NOT SAME AMOUNT!!!");
	}
	@Test
	void testDeckValue3() {
		assertEquals(52, deck.size(), "NOT SAME AMOUNT!!!");
		int queenCnt = 0;
		for (int i=0; i<52; i++) {
			Card card = deck.get(i);
			if(card.value == 12) {
				queenCnt++;
			}
		}
		assertEquals(4, queenCnt, "NOT SAME AMOUNT!!!");
	}
	@Test
	void testDeckSuit2() {
		assertEquals(52, deck.size(), "NOT SAME AMOUNT!!!");
		int spadeCnt = 0;
		for (int i=0; i<52; i++) {
			Card card = deck.get(i);
			if(card.suit == 'S') {
				spadeCnt++;
			}
		}
		assertEquals(13, spadeCnt, "NOT SAME AMOUNT!!!");
	}

	// LOGIC TESTS
	@Test
	void testLogicStraight() {
		int val = ThreeCardLogic.evalHand(straight);
		assertEquals(3, val, "Not a Straight!");
	}
	@Test
	void testLogicPair() {
		int val = ThreeCardLogic.evalHand(pair);
		assertEquals(5, val, "Not a Pair!");
	}
	@Test
	void testLogicThree() {
		int val = ThreeCardLogic.evalHand(three);
		assertEquals(2, val, "Not a Three!");
	}
	@Test
	void testLogicFlush() {
		int val = ThreeCardLogic.evalHand(flush);
		assertEquals(4, val, "Not a Flush!");
	}
	@Test
	void testLogicHigh() {
		int val = ThreeCardLogic.evalHand(high);
		assertEquals(0, val, "Not a High Card!");
	}
	@Test
	void testLogicStrFlush() {
		int val = ThreeCardLogic.evalHand(strFlush);
		assertEquals(1, val, "Not a Straight Flush!");
	}
	@Test
	void testPPWinnings() {
		int val = ThreeCardLogic.evalPPWinnings(strFlush,10);
		assertEquals(400, val, "NOT SAME AMOUNT");
	}
	@Test
	void testPPWinnings2() {
		int val = ThreeCardLogic.evalPPWinnings(high,10);
		assertEquals(0, val, "NOT SAME AMOUNT");
	}
	@Test
	void testPPWinnings3() {
		int val = ThreeCardLogic.evalPPWinnings(flush,10);
		assertEquals(30, val, "NOT SAME AMOUNT");
	}
	@Test
	void testPPWinnings4() {
		int val = ThreeCardLogic.evalPPWinnings(straight,10);
		assertEquals(60, val, "NOT SAME AMOUNT");
	}
	@Test
	void testPPWinnings5() {
		int val = ThreeCardLogic.evalPPWinnings(three,10);
		assertEquals(300, val, "NOT SAME AMOUNT");
	}
	@Test
	void testPPWinnings6() {
		int val = ThreeCardLogic.evalPPWinnings(pair,10);
		assertEquals(10, val, "NOT SAME AMOUNT");
	}
	@Test
	void testEvalHigh() {
		int val = ThreeCardLogic.evalHigh(high);
		assertEquals(10, val, "NOT SAME AMOUNT");
	}
	@Test
	void testCompare(){
		int val = ThreeCardLogic.compareHands(pair,three);
		assertEquals(2, val, "NOT SAME AMOUNT");
	}
	@Test
	void testCompare2(){
		int val = ThreeCardLogic.compareHands(three,pair);
		assertEquals(1, val, "NOT SAME AMOUNT");
	}
	@Test
	void testCompare3(){
		int val = ThreeCardLogic.compareHands(strFlush,three);
		assertEquals(1, val, "NOT SAME AMOUNT");
	}
	@Test
	void testCompare4(){
		int val = ThreeCardLogic.compareHands(pair,pair);
		assertEquals(0, val, "NOT SAME AMOUNT");
	}
	@Test
	void testCompare5(){
		int val = ThreeCardLogic.compareHands(pair,strFlush);
		assertEquals(2, val, "NOT SAME AMOUNT");
	}
	@Test
	void testCompare6(){
		int val = ThreeCardLogic.compareHands(flush,high);
		assertEquals(1, val, "NOT SAME AMOUNT");
	}
	@Test
	void testCompare7(){
		int val = ThreeCardLogic.compareHands(flush,pair);
		assertEquals(1, val, "NOT SAME AMOUNT");
	}
	@Test
	void testCompare8(){
		int val = ThreeCardLogic.compareHands(strFlush,strFlush);
		assertEquals(0, val, "NOT SAME AMOUNT");
	}

}
