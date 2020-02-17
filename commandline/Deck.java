package commandline;

import java.util.ArrayList;

public class Deck {
	private ArrayList<Card> deck;

/*
 *  Deck Constructor contains an ArrayList to store all the cards	
 */
	public Deck() {
		 deck = new ArrayList<>();
	}
	
	
	//Getter
	public ArrayList<Card> getDeck(){
		return deck;
	}
}
