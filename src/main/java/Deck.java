import java.util.ArrayList;
import java.util.Collections;

public class Deck extends ArrayList<Card> {
	private static final long serialVersionUID = 1L;	// Used to rid warning 
	
	Deck(){
		for (int x = 0; x<4;x++) {
			
			for (int i = 1; i<14;i++) {
				Card newCard = new Card('C',2);
				if(x == 0) {
					newCard.suit = 'C';
				}
				else if(x == 1) {
					newCard.suit = 'D';
				}
				else if(x == 2) {
					newCard.suit = 'S';
				}
				else {
					newCard.suit = 'H';
				}
				newCard.value  = i;
				this.add(newCard);
			}
		}
		Collections.shuffle(this);
	}		
	
	
	
	void newDeck(){
		this.clear();
		for (int x = 0; x<4;x++) {
			for (int i = 1; i<14;i++) {
				Card newCard = new Card('C',2);
				if(x == 0) {
					newCard.suit = 'C';
				}
				else if(x == 1) {
					newCard.suit = 'D';
				}
				else if(x == 2) {
					newCard.suit = 'S';
				}
				else {
					newCard.suit = 'H';
				}
				newCard.value  = i;
				this.add(newCard);
			}
		}
		Collections.shuffle(this);
	}
}
