
public class Test {
	public static void PlayerDecks() {
//		Test Player1 deck
		System.err.println("Player1 Deck:");
		if(Deck.Player1.size()>0) {  //Test if the deck is successfully shuffled
			for(int i=0;i<Deck.Player1.size();i++) {
				System.err.println(Deck.Player1.get(i));
			}
		}
//		Test num of left cards in deck
		System.out.println("There are [" + Load.card.size() +"] cards in original deck");
		
//		Test Player2 deck
		System.err.println("Player2 Deck:");
		if(Deck.Player2.size()>0) {  //Test if the deck is successfully shuffled
			for(int i=0;i<Deck.Player2.size();i++) {
				System.err.println(Deck.Player2.get(i));
			}
		}
//		Test num of left cards in deck
		System.out.println("There are [" + Load.card.size() +"] cards in original deck");
		
//		Test Player3 deck
		System.err.println("Player3 Deck:");
		if(Deck.Player3.size()>0) {  //Test if the deck is successfully shuffled
			for(int i=0;i<Deck.Player3.size();i++) {
				System.err.println(Deck.Player3.get(i));
			}
		}
//		Test num of left cards in deck
		System.out.println("There are [" + Load.card.size() +"] cards in original deck");
		
//		Test Player4 deck
		System.err.println("Player4 Deck:");
		if(Deck.Player4.size()>0) {  //Test if the deck is successfully shuffled
			for(int i=0;i<Deck.Player4.size();i++) {
				System.err.println(Deck.Player4.get(i));
			}
		}
//		Test num of left cards in deck
		System.out.println("There are [" + Load.card.size() +"] cards in original deck");
		
//		Test Player5 deck
		System.err.println("Player5 Deck:");
		if(Deck.Player5.size()>0) {  //Test if the deck is successfully shuffled
			for(int i=0;i<Deck.Player5.size();i++) {
				System.err.println(Deck.Player5.get(i));
			}
		}
//		Test num of left cards in deck
		System.out.println("There are [" + Load.card.size() +"] cards in original deck");
		
//		End of print Player Decks
	}
	
	public static void checkShuffle() {
		if(Load.card.size()>0) {  //Test if the deck is successfully shuffled
			for(int i=0;i<Load.card.size();i++) {
				System.out.println(Load.card.get(i));
			}
		}
	}

}
