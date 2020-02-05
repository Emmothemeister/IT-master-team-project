import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
//	static ArrayList<Integer> cardIndex = new ArrayList<>();
	
	static ArrayList<Card> Player1 = new ArrayList<>();
	static ArrayList<Card> Player2 = new ArrayList<>();
	static ArrayList<Card> Player3 = new ArrayList<>();
	static ArrayList<Card> Player4 = new ArrayList<>();
	static ArrayList<Card> Player5 = new ArrayList<>();
	
	static ArrayList<Card> ComparingPile = new ArrayList<>();
	static ArrayList<Integer> Comparing = new ArrayList<>();
	static ArrayList<Card> CommonPile = new ArrayList<>();
	
//	Random r = new Random();
	
	public static void shuffleCards() {
		Collections.shuffle(Load.card);

		if(Load.card.size()>0) {  //Test if the deck is successfully shuffled
			for(int i=0;i<Load.card.size();i++) {
				System.out.println(Load.card.get(i));
			}
		}
		
//		ArrayList<Integer> cardIndex = new ArrayList<>();
//	    for (int i = 0; i < 40; i++) {
//	    	cardIndex.add(i);
//	    }

//        Player1.size();
//        Player1.get(0);//Finding an item by index
//        Player1.set();
//        Player1.remove();
//        Player1.indexOf();//Retrieving the index of an item with a specific value

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
//		hasNext方法发牌
//		while(Load.card.get(i).hasNext()) {
//		    Player1.add(Load.card.get(0));
//	    }
}
