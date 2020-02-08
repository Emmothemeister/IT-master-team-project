
public class Main {
	public static void main(String[] args) {
		Game.Title();          //Print game title
		Game.Start();          //Select Play or Print Statistics
		
		Deck.shuffleCards();
		Deck.deal();
		Test.PlayerDecks();      //Uncomment for testing
		
		Game.draw();              //Test Round1
		Game.roundDetail();
		Game.selectAttribute();
		Game.roundResult();
		Test.PlayerDecks();
		
		Game.draw();              //Test Round2
		Game.roundDetail();
		Game.selectAttribute();
		Game.roundResult();
		Test.PlayerDecks();
		
		Game.draw();              //Test Round3
		Game.roundDetail();
		Game.selectAttribute();
		Game.roundResult();
		Test.PlayerDecks();
		
		Game.draw();              //Test Round4
		Game.roundDetail();
		Game.selectAttribute();
		Game.roundResult();
		Test.PlayerDecks();
		
		Game.draw();              //Test Round5
		Game.roundDetail();
		Game.selectAttribute();
		Game.roundResult();
		Test.PlayerDecks();
           
	}
}
