import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ThreeCardLogic {
	public static int evalHigh(ArrayList<Card> Hand) {
		Card newCard = Hand.get(0);
		int val = newCard.value;
		for(int i = 1; i<Hand.size();i++) {
			newCard = Hand.get(i);
			if( newCard.value > val ) {
				val = newCard.value;
			}
		}
		return val;
	}
	public static int evalThree(ArrayList<Card> Hand) {
		Card newCard;
		Card newCard1;
		for(int i = 0; i< Hand.size();i++) {
			newCard = Hand.get(i);
			for(int x = i+1; x<Hand.size();x++) {
				newCard1 = Hand.get(x);
				if(newCard1.value == newCard.value) {
					for(int y = x+1; y<Hand.size();y++ ) {
						newCard1 = Hand.get(y);
						if(newCard1.value == newCard.value) {
							return 1;
						}
					}
				}
			}
		}
		return 0;
		
	}
	
	public static int evalPair(ArrayList<Card> Hand) {
		Card newCard;
		Card newCard1;
		for(int i = 0; i< Hand.size();i++) {
			newCard = Hand.get(i);
			for(int x = 0; x<Hand.size();x++) {
				newCard1 = Hand.get(x);
				if((i!=x) && (newCard1.value == newCard.value)) {
					return newCard1.value;
				}
			}
		}
		return 0;
		
	}
	
	public static int evalFlush(ArrayList<Card> Hand) {
		Card newCard = Hand.get(0);
		char suit1 = newCard.suit;
		//boolean suit = true;
		for(int i = 0; i< Hand.size();i++) {
			newCard = Hand.get(i);
			if(newCard.suit != suit1 ) {
				return 0;
			}
		}
		return 1;
		
	}
		
	public static void sortHand(ArrayList<Card> Hand) {
		//option 2
		Collections.sort(Hand, new Comparator<Card>() {
			public int compare(Card a, Card b) {
				return Integer.valueOf(a.value).compareTo(b.value);
			}
		});
	}
	
	public static int evalStraight(ArrayList<Card> Hand){
		sortHand(Hand);
		Card newCard = Hand.get(0);
		Card newCard1;
		for(int i = 1; i< Hand.size();i++) {
			newCard1 = Hand.get(i);
			if(newCard.value != newCard1.value - 1) {
				return 0;
			}
			newCard = newCard1;
		}
		return 1;
	}
	
	public static int evalHand(ArrayList<Card> Hand) {
		boolean flush;
		boolean straight;
		boolean pair;
		boolean three;
		int x = evalFlush(Hand);
		if( x == 1) {
			flush = true;
		}
		else {
			flush = false;
		}
		x = evalStraight(Hand);
		if( x == 1 ) {
			straight = true;
		}
		else {
			straight = false;
		}
		x = evalPair(Hand);
		if(x>0) {
			pair =true;
			
		}
		else {
			pair = false;
			
		}
		x = evalThree(Hand);
		if (x==1) {
			three = true;
		}
		else {
			three = false;
		}
		if(flush == true && straight == true) {
			return 1;
		}
		else if (three == true) {
			return 2;
		}
		else if (straight == true) {
			return 3;
		}
		else if (flush == true) {
			return 4;
		}
		else if(pair == true){
			return 5;
		}
		else {
			return 0;
		}
				
		
		
	}
	public static int evalPPWinnings(ArrayList<Card> Hand, int bet) {
		int val1 = evalHand(Hand);
		int winnings = 0;
		if (val1 == 1) {
			winnings = bet * 40;
		}
		else if (val1 == 2) {
			winnings = 30 * bet;
		}
		else if (val1 == 3) {
			winnings = 6 * bet;
		}
		else if (val1 == 4) {
			winnings =  3 * bet;
		}
		else if (val1 == 5) {
			winnings = bet;
		}
		return winnings;
	}
	
	
	public static int compareHands(ArrayList<Card> dealer, ArrayList<Card> player) {
		int val1 = evalHand(dealer);
		int val2 = evalHand(player);
		if(val1 != 0 && val1 < val2) {
			return 1;
		}
		else if (val2 != 0 && val2 < val1) {
			return 2;
		}
		else if (val1 == 0 && evalHigh(dealer) < 12) {
			return 0;					// Dealer doesn't have Q High
		}
		else if (val2 == 0 && val1 != 0) {
			return 1;
		}
		else if (val1 == 0 && val2 != 0) {
			return 2;
		}
		else if (val2 == 0 && val1 == 0) {
			int num = evalHigh(dealer);
			int num2 = evalHigh(player);
			if(num>num2) {
				return 1;
			}
			else if(num2>num) {
				return 2;
			}
			else {
				return 0;
			}
			
		}
		else if (val1 == val2) {
			if( (val1==1) || (val1==2) || (val1==3) || (val1==4)) {
				int num = evalHigh(dealer);
				int num2 = evalHigh(player);
				if(num>num2) {
					return 1;
				}
				else if(num2>num) {
					return 2;
				}
				else {
					return 0;
				}
			} else if (val1 == 5) {		// its a pair
				int num = evalPair(dealer);
				int num2 = evalPair(player);
				if(num>num2) {
					return 1;
				}
				else if(num2>num) {
					return 2;
				}
				else {				// THEY EQUAL
					num = evalHigh(dealer);
					num2 = evalHigh(player);
					if(num>num2) {
						return 1;
					}
					else if(num2>num) {
						return 2;
					}
					else {
						return 0;
					}
				}
			} else {
				return 0;
			}
		}
		else {
			return 0;
		}
		
	}

}
