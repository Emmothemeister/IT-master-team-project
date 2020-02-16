package cmdmodel;

import java.util.ArrayList;

public class Deck {
	private static ArrayList<Card> deck;

/*
 *  Deck Constructor contains an ArrayList to store all the cards	
 */
	public Deck() {
		 deck = new ArrayList<>();
	}
	
	
	//Getter
	public static ArrayList<Card> getDeck(){
		return deck;
	}
}
