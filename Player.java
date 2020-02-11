import java.util.ArrayList;

public class Player {
	private int playerIndex;
	private ArrayList<Card> playerDeck;
	
	public Player(int i, ArrayList<Card> pd) {
		playerIndex = i;
		playerDeck = pd;
		
	}
	
	public int getPlayerIndex() {
		return playerIndex;
	}
	
	public ArrayList<Card> getPlayerDeck() {
		return playerDeck;
	}
		
}
