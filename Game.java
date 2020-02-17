import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Game {
	//Class attributes are defined as follows:
	// For determining write game log or not
	private boolean writeLogs;
	
	//For SQL
	private int nRound = 1;
	private int nDraws;
	private int roundWinnerIndex;
	
	//For Command Line Inputs
	private int selection;
	private int nPlayers;
	private int attribute;
	
	//For computing max value	
	private Object Max;
	private ArrayList<Card> ComparingPile = new ArrayList<>();
	private ArrayList<Integer> CompValue = new ArrayList<>();
	private ArrayList<Integer> maxList = new ArrayList<>();
	
	//For game mechanics
	private ArrayList<Player> playerList = new ArrayList<>();
	private ArrayList<Card> CommonPile = new ArrayList<>();
	private ArrayList<Integer> AIpicking = new ArrayList<>();
	
	//Others
	private String info;
	private String printer;
	private String winner;
	private int YourDeckSize;
	
	
/*
 *  Start the game with this constructor which contains a newGame method
 */
	public Game(boolean writeLogs) {
		this.writeLogs = writeLogs;
		newGame();
	}
	
	//One Game
	public void newGame() {
		start();     //This method would proceed automatically
		newRound();  //This method used to start a new round
	}
	
	//One round of game
	public void newRound() {
		draw();
		roundDetail();
		selectAttribute();
		roundResult();
		checkGameEnd();
	}
	
	//This is the start of the game
	public void start() {
		String startingMessage = "Do you want to see past results or play a game?\n"
				+ "   1: Print Game Statistics\n   2: Play game\n"
				+ "Enter the number for your selection: ";
		info = startingMessage;
		System.out.print(info);
		
		Scanner s = new Scanner(System.in);
		selection = s.nextInt();
		//Only number 1 or 2 is valid
		if(selection < 1 || selection > 2) {
			String wrongSelection = "\n< Please input a correct number >";
			info = wrongSelection;
			System.out.println(info);
			start();              //Restart
		}
		select();                 //Proceed to next step
	}
	
	//This is the method for selecting Game Statistics or Start to play
	public void select() {
		if (selection == 1) {
			JDBCUtils.getStatistics();
			System.out.println();
			start();              //After printing statistics, start again
		} else if (selection == 2) {
			startToPlay();        //Proceed to next step
		}
	}
	
	public void startToPlay() {
		new Deck();               //Create a deck to store all the cards
		new Load(writeLogs);      //Read the file and load cards into deck
		shuffleCards(writeLogs);           //Shuffle all the cards in the deck
		createPlayer();           //Create player objects
		deal();                   //Deal cards from deck to players
//		TestPlayerDecks();        //For Test
	}
	
	public void shuffleCards(boolean writeLogs) {  
		Collections.shuffle(Deck.getDeck());
		if(writeLogs) {
			checkShuffle();
		}
		           //check if the deck is successfully shuffled
	}
	
	public void createPlayer() {
		String chooseNumOfPlayers = "Please choose number of players:[ 2 - 5 ]";
		info = chooseNumOfPlayers;
		System.out.print(info);
		Scanner s2 = new Scanner(System.in);
		nPlayers = s2.nextInt();
		//Number of players should be between 2 to 5
		if(nPlayers < 2 || nPlayers > 5) {
			String wrongNumOfPlayers = "\n< Please input a correct number of players>";
			info = wrongNumOfPlayers;
			System.out.println(info);
			createPlayer();
		}else {                   //Correct input, then create Player Objects
			for(int i = 0; i < nPlayers; i++) {
				playerList.add(new Player(i, new ArrayList<Card>()));
			}
		}
	}
/*
 *  Deal cards from deck to players
 *  Every time deal a card, player would get 1 card,
 *  and this card would be removed from original deck.
 */	
	public void deal() {
		int i = 0;
		while(i < playerList.size() && Deck.getDeck().size() > 0) {
			playerList.get(i).getPlayerDeck().add(Deck.getDeck().get(0));
			Deck.getDeck().remove(0);
			
			if(i != (playerList.size()-1)) {
				i++;
			} else {   //After finishing dealing to the last player,
				i = 0; //deal to the first player next
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
	
	public void checkIfHumanIsAlive() {
		if(playerList.get(0).getPlayerIndex() == 0) { //Alive
			YourDeckSize = playerList.get(0).getPlayerDeck().size();
			System.out.println(ComparingPile.get(0));

		}else {
			YourDeckSize = 0;
			System.out.println("   < You are out >");
		}
	}

/*
 *  Show the detail of the card you drew
 */	
	public void roundDetail() {
		if(nRound == 1) {
			String gameStart = "\n\nGame Start"; //First Round show 'Game Start'
			info = gameStart;
			System.out.println(info);
		}else {
			System.out.println();                //Other Rounds add an extra blank line
		}
		String round = "Round " + nRound + 
				"\nRound " + nRound + ": Players have drawn their cards";		
		info = round;
		System.out.println(info);
/*
 * Since your first card has already drawn from your deck and added into the ComparingPile,
 * the detail of your first card can be accessed by reading the first card in ComparingPile
 */
		checkIfHumanIsAlive();
		
		String yourDeckSize = "There are [" + YourDeckSize + "] cards in your deck";
		info = yourDeckSize;
		System.out.println(info);

	}

/*
 *  This method firstly determine which of human or AI to select card attribute.
 *  If it is human's turn, then call the humanSelectAttribute() method.
 *  If it is AI's turn, then call the AISelectAttribute() method.
 *  In the first round, the player to select attribute is determined randomly.
 *  Finally call the calculate() method to compute game result.
 */	
	public void selectAttribute() {
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

/*
 *  Use scanner for human player to input a number
 */
	public void humanSelectAttribute() {
		String showCategories = "It is your turn to select a category, the categories are:"
				+ "   1: Size\n   2: Speed\n   3: Range\n   4: Firepower\n   5: Cargo\n"
				+ "Enter the number for your attribute: ";
		info = showCategories;
		System.out.print(info);
		Scanner a = new Scanner(System.in);
		attribute = a.nextInt();
		
		if(attribute < 1 || attribute > 5) {
			String wrongAttribute = "\n< Please input a correct index of attribute>";
			info = wrongAttribute;
			System.out.println(info);
			humanSelectAttribute();
		}
	}

/*
 *  Create an ArrayList called AIpicking to store the card information
 *  Firstly, find which AI is picking attribute
 *  Then, load this AI's card detail and store into AIpicking
 *  Afterwards, use Collection.max()method to find the max value
 *  and find its index store as the value for attribute variable
 *  Finally,reset AIpicking list to null.
 */
	public void AISelectAttribute() {
		AIpicking = new ArrayList<>();
		int AI_ID = 0;
		for(int i = 0; i < playerList.size();i++) {
			if(roundWinnerIndex == playerList.get(i).getPlayerIndex()) {
				AI_ID = i;
			}
		}
		AIpicking.add(ComparingPile.get(AI_ID).getSize());//
		AIpicking.add(ComparingPile.get(AI_ID).getSpeed());
		AIpicking.add(ComparingPile.get(AI_ID).getRange());
		AIpicking.add(ComparingPile.get(AI_ID).getFirepower());
		AIpicking.add(ComparingPile.get(AI_ID).getCargo());
		attribute = AIpicking.indexOf(Collections.max(AIpicking)) + 1;
		AIpicking = null;
	}
	
/*
 *  Store chosen attribute values into CompValue list.
 *  Then find the largest value
 */
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

/*
 *  Every time find a max value, store the object into maxList
 *  If the maxList has more than 1 element, then this round is a draw.
 */
	public void takeMax() {
		Max = Collections.max(CompValue);                         //Max is an Integer Object
//		System.err.println("Max value is: " + Max.toString());    //For test
		for(int i = 0; i < CompValue.size(); i++) {               //CompValue.size() is number of Players
			if(CompValue.get(i) == Max) {
				maxList.add(i);
//				System.err.println("Player" + (i+1));             //For test, who share the same max value
			}
		}
	}
	
/*
 *  Move cards from ComparingPile to CommonPile
 */
	public void addToCommonPile() {
		for (int i = 0; i < ComparingPile.size(); i++) {
			CommonPile.add(ComparingPile.get(i));
		}
	}

/*
 *  Display round result and then send message to database
 */
	public void roundResult() {
		addToCommonPile();                  //add to common pile
		if(maxList.size() > 1) {            //it's a draw
			String roundIsDraw = "Round " + nRound + ": This round was a Draw, "
					+ "common pile now has [" + CommonPile.size() 
					+ "] cards\nThe winning card was [" 
					+ ComparingPile.get(CompValue.indexOf(Max)).getDescription() 
					+ "]:";
			info = roundIsDraw;
			System.out.println(info);
			
//			//print common pile for test
//			System.err.println("<TEST> Common Pile now has:");
//			for (int i = 0; i < CommonPile.size(); i++) {
//				System.err.println(CommonPile.get(i));
//			}
			
			nDraws++;
			printResult();
			JDBCUtils.updateONEGAMEdraw(nRound);
			
		} else {  // not a draw
			roundWinnerIndex = playerList.get(CompValue.indexOf(Max)).getPlayerIndex();
			winnerName();
			String winnerDetails = "Round " + nRound + ": Player " + winner + " won this round\n"
					+ "The winning card was ["
					+ ComparingPile.get(CompValue.indexOf(Max)).getDescription()
					+ "]:";
			info = winnerDetails;
			System.out.println(info);
			printResult();
			
			//Winner gain all the cards from common pile
			for(int i = 0; i < CommonPile.size(); i++) {
				playerList.get(CompValue.indexOf(Max)).getPlayerDeck().add(CommonPile.get(i)); //bug here
			}
			CommonPile.clear();
			JDBCUtils.updateONEGAME(nRound,roundWinnerIndex);
		}
		CompValue.clear();
		maxList.clear();
		removePlayer();
	}

/*
 *  Every time a player lost all the cards, this player is lose and he would be removed.
 *  If human player is lose and removed, his deck size is set to be 0. Otherwise, AI1 
 *  would fill human's position in the 'playerList' ArrayList.
 */
	public void removePlayer() {		
		int i = 0;
		while(i < playerList.size()) {
			if(playerList.get(i).getPlayerDeck().size() == 0) {
				playerList.remove(i);
				i = 0;
				continue;
			} else {
				i++;
			}
		}
	}
	
	public void checkGameEnd() {
		String ENTER = "Please press [ENTER] to continue";
		info = ENTER;
		System.out.println(info);
//		new Scanner(System.in).nextLine();
		if(playerList.size() == 1) {
            //This game is over
			String gameEnd = "\n\nGame End\n";
			info = gameEnd;
			System.out.println(info);
			//Update data to database
			JDBCUtils.updateALLGAMES(roundWinnerIndex, nRound, nDraws);
			gameResult();
			
			nRound = 1;         //Reset number of rounds
			playerList.clear(); //Clear all the players
			JDBCUtils.resetONEGAME();
			newGame();
		}else {
			nRound++;
			newRound();
		}
	}
	
	public void winnerName() {
		if(roundWinnerIndex == 0) {
			winner = "You";
		}else {
			winner = "AI Player " + roundWinnerIndex;			
		}
	}
	
	
	public void gameResult() {
		winnerName();
		String gameResult = "The overall winner was " + winner + "\nScores:";
		info = gameResult;
		System.out.println(info);
		//Access to database
		JDBCUtils.getONEGAMERecord();
		System.out.println("\n");
	}
		
	public void printResult() {
		String size      = "   > Size:       " 
	+ ComparingPile.get(CompValue.indexOf(Max)).getSize();
		String speed     = "   > Speed:      " 
	+ ComparingPile.get(CompValue.indexOf(Max)).getSpeed();
		String range     = "   > Range:      "
	+ ComparingPile.get(CompValue.indexOf(Max)).getRange();
		String firepower = "   > Firepower:  "
	+ ComparingPile.get(CompValue.indexOf(Max)).getFirepower();
		String cargo     = "   > Cargo:      "
	+ ComparingPile.get(CompValue.indexOf(Max)).getCargo();
		
		String markSize      = size      + " <--";
		String markSpeed     = speed     + " <--";
		String markRange     = range     + " <--";
		String markFirepower = firepower + " <--";
		String markCargo     = cargo     + " <--";
		
		if(attribute == 1) {
			info = markSize;       //  <--
			System.out.println(info);
			info = speed;
			System.out.println(info);
			info = range;
			System.out.println(info);
			info = firepower;
			System.out.println(info);
			info = cargo;
			System.out.println(info);
			
		} else if (attribute == 2) {
			info = size;
			System.out.println(info);
			info = markSpeed;      //  <--
			System.out.println(info);
			info = range;
			System.out.println(info);
			info = firepower;
			System.out.println(info);
			info = cargo;
			System.out.println(info);
			
		} else if (attribute == 3) {
			info = size;
			System.out.println(info);
			info = speed;
			System.out.println(info);
			info = markRange;      //  <--
			System.out.println(info);
			info = firepower;
			System.out.println(info);
			info = cargo;
			System.out.println(info);
			
		} else if (attribute == 4) {
			info = size;
			System.out.println(info);
			info = speed;
			System.out.println(info);
			info = range;
			System.out.println(info);
			info = markFirepower;  //  <--
			System.out.println(info);
			info = cargo;
			System.out.println(info);
			
		} else if (attribute == 5) {
			info = size;
			System.out.println(info);
			info = speed;
			System.out.println(info);
			info = range;
			System.out.println(info);
			info = firepower;
			System.out.println(info);
			info = markCargo;      //  <--
			System.out.println(info);
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
			
			String cardList = "";
			String cl = "";
			
			for(int i = 0; i < Deck.getDeck().size();i++) {
				cl = Deck.getDeck().get(i).testLog() + "\n";
				cardList += cl;
			}
			TestLog.record("After shuffle:");
			TestLog.record(cardList);
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
	
	//Choose number of players, 5 buttons
	public void twoPlayers() {
		for(int i = 0; i < 2; i++) {
			playerList.add(new Player(i, new ArrayList<Card>()));
		}
	}
	public void threePlayers() {
		for(int i = 0; i < 3; i++) {
			playerList.add(new Player(i, new ArrayList<Card>()));
		}
	}
	public void fourPlayers() {
		for(int i = 0; i < 4; i++) {
			playerList.add(new Player(i, new ArrayList<Card>()));
		}
	}
	public void fivePlayers() {
		for(int i = 0; i < 5; i++) {
			playerList.add(new Player(i, new ArrayList<Card>()));
		}
	}
	public void Start() {
		for(int i = 0; i < 3; i++) {
			playerList.add(new Player(i, new ArrayList<Card>()));
		}
	}
	
	public int getRoundWinnerIndex() {
		return roundWinnerIndex;
	}
	
}
