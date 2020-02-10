import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
	private static ArrayList<Card> deck;

	public Deck() {
		 deck = new ArrayList<>();
	}
	
	public static ArrayList<Card> getDeck(){
		return deck;
	}
}
