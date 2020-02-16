
import java.util.ArrayList;

public class Player {
	private int playerIndex;
	private ArrayList<Card> playerDeck;
	
/*
 *  Constructor:
 *  Each Player object contains a playerIndex as a unique ID
 *  and an ArrayList to store cards called playerDeck
 */
	public Player(int i, ArrayList<Card> pd) {
		playerIndex = i;
		playerDeck = pd;	
	}
	
	
	//Getters
	public int getPlayerIndex() {
		return playerIndex;
	}
	
	public ArrayList<Card> getPlayerDeck() {
		return playerDeck;
	}
		
}
