import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Game {
	private int numOfGames = 1;////SQL number of Games	
	private int longestGame = 0;
	private int nRound = 1;
	private Object Max;
	private int attribute;
	
	private int selection;
	private int nPlayers;
	private ArrayList<Player> playerList = new ArrayList<>();
	
	private ArrayList<Card> ComparingPile = new ArrayList<>();
	private ArrayList<Integer> CompValue = new ArrayList<>();
	private ArrayList<Integer> maxList = new ArrayList<>();
	private ArrayList<Card> CommonPile = new ArrayList<>();
	private ArrayList<Integer> AIpicking = new ArrayList<>();
	
	private int roundWinnerIndex;
	private String winner;
	
	
	public Game() {
		newGame();
	}
	
	public void newGame() {
		start();
		shuffleCards();
		createPlayer();
		deal();
//		TestPlayerDecks();  //Test
		draw();
		roundDetail();
		selectAttribute();
		roundResult();
		checkGameEnd();
	}
	
	public void newRound() {
		draw();
		roundDetail();
		selectAttribute();
		roundResult();
		checkGameEnd();
	}
	
	public void start() {
		System.out.println("Do you want to see past results or play a game?");
		System.out.println("   1: Print Game Statistics");
		System.out.println("   2: Play game");
		System.out.print("Enter the number for your selection: ");
		
		Scanner s = new Scanner(System.in);
		selection = s.nextInt();
		//Only number 1 or 2 is valid
		if(selection < 1 || selection > 2) { 
			System.out.println("\n< Please input a correct number >");
			start();
		}
		select();
	}
	
	public void select() {
		if (selection == 1) {
//			Print Game Statistics
			System.out.println();
			start();                            //After printing statistics, start again
		} else if (selection == 2) {
			new Deck();
			new Load();                         //Load cards
		}
	}
	
	public void shuffleCards() {
		Collections.shuffle(Deck.getDeck());
//		checkShuffle();
	}
	
	public void createPlayer() {
		System.out.print("Please choose number of players:[ 2 - 5 ]");
		Scanner s2 = new Scanner(System.in);
		nPlayers = s2.nextInt();
		//Number of players should be between 2 to 5
		if(nPlayers < 2 || nPlayers > 5) { 
			System.out.println("\n< Please input a correct number of players>");
			createPlayer();
		}else {
			for(int i = 0; i < nPlayers; i++) {
				playerList.add(new Player(i, new ArrayList<Card>()));
			}
		}
	}
	
	public void deal() {
		int i = 0;
		while(i < playerList.size() && Deck.getDeck().size() > 0) {
			playerList.get(i).getPlayerDeck().add(Deck.getDeck().get(0));
			Deck.getDeck().remove(0);
			
			if(i != (playerList.size()-1)) {
				i++;
			} else {
				i = 0;
			}
		}
	}
	
/*
 *  Put first card of every player into comparing pile
 */
	public void draw() {	
		
		for(int i = 0; i < playerList.size(); i++) {
			ComparingPile.add(playerList.get(i).getPlayerDeck().get(0));
			playerList.get(i).getPlayerDeck().remove(0);
		}
	}
/*
 *  Show the detail of the card you drew
 */	
	public void roundDetail() {
		if(nRound == 1) {
			System.out.println("\n\nGame Start");
		}else {
			System.out.println();
		}
		System.out.println("Round " + nRound + 
				"\nRound " + nRound + ": Players have drawn their cards");

		System.out.println(ComparingPile.get(0));  //print detail of your first card
		System.out.println("There are [" + playerList.get(0).getPlayerDeck().size() + "] cards in your deck");		
	}
	
	public void takeMax() {
		Max = Collections.max(CompValue);                         //Max is an Integer Object
//		System.err.println("Max value is: " + Max.toString());    //For test
		for(int i = 0; i < CompValue.size(); i++) {               //CompValue.size() is number of Players
			if(CompValue.get(i) == Max) {
				maxList.add(i);
//				System.err.println("Player" + (i+1));             //For test
			}
		}
	}
	
	public void humanSelectAttribute() {
		System.out.println("It is your turn to select a category, the categories are:");
		System.out.println("   1: Size\n   2: Speed\n   3: Range\n   4: Firepower\n   5: Cargo");
		System.out.print("Enter the number for your attribute: ");
		Scanner a = new Scanner(System.in);
		attribute = a.nextInt();
		
		if(attribute < 1 || attribute > 5) { 
			System.out.println("\n< Please input a correct index of attribute>");
			humanSelectAttribute();
		}
	}
	
	public void calculate() {
		
		if(attribute == 1) {
			for(int i = 0; i < playerList.size(); i++) {
				CompValue.add(ComparingPile.get(i).getSize());
			}
		}else if(attribute == 2) {
			for(int i = 0; i < playerList.size(); i++) {
				CompValue.add(ComparingPile.get(i).getSpeed());
			}			
		}else if(attribute == 3) {
			for(int i = 0; i < playerList.size(); i++) {
				CompValue.add(ComparingPile.get(i).getRange());
			}			
		}else if(attribute == 4) {
			for(int i = 0; i < playerList.size(); i++) {
				CompValue.add(ComparingPile.get(i).getFirepower());
			}			
		}else if(attribute == 5) {
			for(int i = 0; i < playerList.size(); i++) {
				CompValue.add(ComparingPile.get(i).getCargo());
			}
		}
		takeMax();
	}
	
	public void AISelectAttribute() {
		AIpicking = new ArrayList<>();
		int AIplaying = 0;
		for(int i = 0; i < playerList.size();i++) {
			if(roundWinnerIndex == playerList.get(i).getPlayerIndex()) {
				AIplaying = i;
			}
		}
		AIpicking.add(ComparingPile.get(AIplaying).getSize());//
		AIpicking.add(ComparingPile.get(AIplaying).getSpeed());
		AIpicking.add(ComparingPile.get(AIplaying).getRange());
		AIpicking.add(ComparingPile.get(AIplaying).getFirepower());
		AIpicking.add(ComparingPile.get(AIplaying).getCargo());
		attribute = AIpicking.indexOf(Collections.max(AIpicking)) + 1;
		AIpicking = null;
	}
	
	public void selectAttribute() {
//		In the first round, the player to select attribute is determined randomly
		if(nRound == 1) {
			Random r = new Random();
			roundWinnerIndex = r.nextInt(nPlayers);
		}
		
		if(roundWinnerIndex == 0) {
			humanSelectAttribute();
		}else {
			AISelectAttribute();
		}
		calculate();
	}
	
	public void addToCommonPile() {
		for (int i = 0; i < ComparingPile.size(); i++) {
			CommonPile.add(ComparingPile.get(i));
		}
	}
	
	public void roundResult() {
		addToCommonPile();                  //add to common pile
		if(maxList.size() > 1) {            //it's a draw			
			System.out.println("Round " + nRound + ": This round was a Draw, "
					+ "common pile now has [" + CommonPile.size() 
					+ "] cards\nThe winning card was [" 
					+ ComparingPile.get(CompValue.indexOf(Max)).getDescription() 
					+ "]:");
			
			//print common pile for test
//			System.err.println("<TEST> Common Pile:");
//			for (int i = 0; i < CommonPile.size(); i++) {
//				System.err.println(CommonPile.get(i));
//			}
			printResult();
			
		} else {  // not a draw
			roundWinnerIndex = playerList.get(CompValue.indexOf(Max)).getPlayerIndex();
			winnerName();
			System.out.println("Round " + nRound + ": Player " + winner + " won this round");
			System.out.println("The winning card was [" 
					+ ComparingPile.get(CompValue.indexOf(Max)).getDescription()
					+ "]:");
			printResult();
			for(int i = 0; i < CommonPile.size(); i++) {
				playerList.get(CompValue.indexOf(Max)).getPlayerDeck().add(CommonPile.get(i)); //bug here
			}
			CommonPile.clear();
////SQL		playerList.get(roundWinnerIndex) is the winner object, player"roundWinnerIndex" win time + 1;
		}
		CompValue.clear();
		maxList.clear();
		removePlayer();
		nRound++;
		nextRound();
	}

	//nextRound for Command Line
	public void nextRound() { //press [ENTER] to continue
		System.out.println("Please press [ENTER] to continue");
		new Scanner(System.in).nextLine();
	}
	
	public void removePlayer() {
		int i = 0;
		while(i < playerList.size()) {
			if(playerList.get(i).getPlayerDeck().size() == 0) {
//				playerList.get(i).removePlayerDeck();
				playerList.remove(i);
				i = 0;
				continue;
			} else {
				i++;
			}
		}
	}
	
	public void checkGameEnd() {
		if(playerList.size() == 1) {
//			Game End
			System.out.println("\n\nGame End\n");
			gameResult();
			checkLongestGame();
			nRound = 1;
			numOfGames++;
////SQL     numOfGames++
			playerList.clear();
			newGame();
		}else {
			newRound();
		}
	}
	
	public void winnerName() {
		if(roundWinnerIndex == 0) {
			winner = "You";
////SQL		Number of Human Wins + 1
		}else {
			winner = "AI Player " + roundWinnerIndex;
////SQL		Number of AI Wins + 1			
		}
	}
	
	public void checkLongestGame() {
		if(nRound > longestGame) {
			longestGame = nRound;
////SQL		Longest Game			
		}
	}
	
	public void gameResult() {
		winnerName();
		System.out.println("The overall winner was " + winner + "\nScores:");
		for(int i = 0; i < playerList.size(); i++) {
//			System.out.println("   " + winner + ": " + playerList.get(i).getWinTimes());
////SQL Win times
		}
		System.out.println("\n");
	}
		
	public void printResult() {
		if(attribute == 1) {
			System.out.println("   > Size:       " 
		+ ComparingPile.get(CompValue.indexOf(Max)).getSize() + " <--");
			System.out.println("   > Speed:      " 
		+ ComparingPile.get(CompValue.indexOf(Max)).getSpeed());
			System.out.println("   > Range:      " 
		+ ComparingPile.get(CompValue.indexOf(Max)).getRange());
			System.out.println("   > Firepower:  " 
		+ ComparingPile.get(CompValue.indexOf(Max)).getFirepower());
			System.out.println("   > Cargo:      " 
		+ ComparingPile.get(CompValue.indexOf(Max)).getCargo());
		} else if (attribute == 2) {
			System.out.println("   > Size:       " 
		+ ComparingPile.get(CompValue.indexOf(Max)).getSize());
			System.out.println("   > Speed:      " 
		+ ComparingPile.get(CompValue.indexOf(Max)).getSpeed() + " <--");
			System.out.println("   > Range:      " 
		+ ComparingPile.get(CompValue.indexOf(Max)).getRange());
			System.out.println("   > Firepower:  " 
		+ ComparingPile.get(CompValue.indexOf(Max)).getFirepower());
			System.out.println("   > Cargo:      " 
		+ ComparingPile.get(CompValue.indexOf(Max)).getCargo());
		} else if (attribute == 3) {
			System.out.println("   > Size:       " 
		+ ComparingPile.get(CompValue.indexOf(Max)).getSize());
			System.out.println("   > Speed:      " 
		+ ComparingPile.get(CompValue.indexOf(Max)).getSpeed());
			System.out.println("   > Range:      " 
		+ ComparingPile.get(CompValue.indexOf(Max)).getRange() + " <--");
			System.out.println("   > Firepower:  " 
		+ ComparingPile.get(CompValue.indexOf(Max)).getFirepower());
			System.out.println("   > Cargo:      " 
		+ ComparingPile.get(CompValue.indexOf(Max)).getCargo());
		} else if (attribute == 4) {
			System.out.println("   > Size:       " 
		+ ComparingPile.get(CompValue.indexOf(Max)).getSize());
			System.out.println("   > Speed:      " 
		+ ComparingPile.get(CompValue.indexOf(Max)).getSpeed());
			System.out.println("   > Range:      " 
		+ ComparingPile.get(CompValue.indexOf(Max)).getRange());
			System.out.println("   > Firepower:  " 
		+ ComparingPile.get(CompValue.indexOf(Max)).getFirepower() + " <--");
			System.out.println("   > Cargo:      " 
		+ ComparingPile.get(CompValue.indexOf(Max)).getCargo());
		} else if (attribute == 5) {
			System.out.println("   > Size:       " 
		+ ComparingPile.get(CompValue.indexOf(Max)).getSize());
			System.out.println("   > Speed:      " 
		+ ComparingPile.get(CompValue.indexOf(Max)).getSpeed());
			System.out.println("   > Range:      " 
		+ ComparingPile.get(CompValue.indexOf(Max)).getRange());
			System.out.println("   > Firepower:  " 
		+ ComparingPile.get(CompValue.indexOf(Max)).getFirepower());
			System.out.println("   > Cargo:      " 
		+ ComparingPile.get(CompValue.indexOf(Max)).getCargo() + " <--");
		}
		ComparingPile.clear();
//		TestPlayerDecks();          //For test
	}
	
	//Tests:
	
	public void TestPlayerDecks() { //For test
		System.err.println("=======================================");
		for(int i = 0; i < playerList.size(); i++) {
			System.err.println("Player" + playerList.get(i).getPlayerIndex() + " Deck:");
			for(int j=0; j < playerList.get(i).getPlayerDeck().size();j++) {
				System.err.println(playerList.get(i).getPlayerDeck().get(j));//Test if the deck is successfully shuffled
			}
			System.err.println("=======================================");
		}
		System.err.println("Original deck now has [" + Deck.getDeck().size() + "] card left");
	}
	
	public void checkShuffle() {  //For test
		if(Deck.getDeck().size()>0) {  //Test if the deck is successfully shuffled
			for(int i=0;i<Deck.getDeck().size();i++) {
				System.out.println(Deck.getDeck().get(i));
			}
		}
	}
	
// Methods for online mode	
	//displayPlayer
	public boolean displayPlayer(int playerIndex) {
		if(playerList.get(playerIndex) != null) {
			return true;
		}else {
			return false;
		}
	}
	//mark
	public boolean markAttribute(int attributeIndex) {
		if(attribute == attributeIndex) {
			return true;
		}else {
			return false;
		}
	}
	//pick attributes
	public void pickSize() {
		attribute = 1;
		calculate();
	}
	public void pickSpeed() {
		attribute = 2;
		calculate();
	}
	public void pickRange() {
		attribute = 3;
		calculate();
	}
	public void pickFirepower() {
		attribute = 4;
		calculate();
	}
	public void pickCargo() {
		attribute = 5;
		calculate();
	}
}
