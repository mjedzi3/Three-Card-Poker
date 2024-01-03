import java.util.ArrayList;

public class Dealer extends Deck{
	private static final long serialVersionUID = 1L;	// Used to rid of Warning
	Deck theDeck;
	ArrayList<Card> dealersHand;
	int cardTotal = 52;				// used to store deck total
	
	Dealer(){
		theDeck = new Deck();
		dealersHand = new ArrayList<Card>();
	}
	
	public ArrayList<Card> dealHand(){ 
		if (cardTotal < 35) {
			theDeck.newDeck();
			cardTotal = 52;
		}
		ArrayList<Card> hand = new ArrayList<Card>();
		
		Card newCard;
		
		newCard = theDeck.get(0);
		hand.add(newCard);
		
		newCard = theDeck.get(1);
		hand.add(newCard);
		
		newCard = theDeck.get(2);
		hand.add(newCard);
		
		theDeck.remove(0);
		theDeck.remove(1);
		theDeck.remove(2);
		
		cardTotal = cardTotal - 3;
		return hand;
	}

}
