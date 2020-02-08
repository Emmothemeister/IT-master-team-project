import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
	static ArrayList<Card> Player1 = new ArrayList<>();
	static ArrayList<Card> Player2 = new ArrayList<>();
	static ArrayList<Card> Player3 = new ArrayList<>();
	static ArrayList<Card> Player4 = new ArrayList<>();
	static ArrayList<Card> Player5 = new ArrayList<>();
	
	static ArrayList<Card> ComparingPile = new ArrayList<>();
	static ArrayList<Integer> CompValue = new ArrayList<>();
	static ArrayList<Card> CommonPile = new ArrayList<>();
	
	public static void shuffleCards() {
		Collections.shuffle(Load.card);
//		Test.checkShuffle();
	}
	
	public static void deal() {
		for(int i = 0; i < 8; i++) {
			Player1.add(Load.card.get(0));
			Load.card.remove(0);		
		}
		for(int i = 0; i < 8; i++) {
			Player2.add(Load.card.get(0));
			Load.card.remove(0);		
		}
		for(int i = 0; i < 8; i++) {
			Player3.add(Load.card.get(0));
			Load.card.remove(0);		
		}
		for(int i = 0; i < 8; i++) {
			Player4.add(Load.card.get(0));
			Load.card.remove(0);		
		}
		for(int i = 0; i < 8; i++) {
			Player5.add(Load.card.get(0));
			Load.card.remove(0);		
		}		
	}
}
