import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Game {
	private int nRound = 1;
	private ArrayList<Integer> maxList = new ArrayList<>();
	private Object Max;
	private int attribute;
	
	private int selection;
	private int nPlayers;
	private ArrayList<ArrayList<Card>> Player = new ArrayList<>();
	
	private ArrayList<Card> ComparingPile = new ArrayList<>();
	private ArrayList<Integer> CompValue = new ArrayList<>();
	private ArrayList<Card> CommonPile = new ArrayList<>();	
	
	
	public Game() {
		start();
		shuffleCards();
		createPlayer();
		deal();
		TestPlayerDecks();  //Test
		draw();
		roundDetail();
		selectAttribute();
		roundResult();
	}
	
	public void start() {
		System.out.println("Do you want to see past results or play a game?");
		System.out.println("   1: Print Game Statistics");
		System.out.println("   2: Play game");
		System.out.print("Enter the number for your selection: ");
		
		Scanner s = new Scanner(System.in);
		selection = s.nextInt();
		select();
	}
	
	public void select() {
		if (selection == 1) {
//			Print Game Statistics
			start();                            //After printing statistics, start again
		} else if (selection == 2) {
			new Deck();
			new Load();                         //Load cards
		}
	}
	
	public void shuffleCards() {
		Collections.shuffle(Deck.getDeck());
		checkShuffle();
	}
	
	public void createPlayer() {
		System.out.print("Choose Number of Players: (No more than " + Deck.getDeck().size() + " players)");
		Scanner s2 = new Scanner(System.in);
		nPlayers = s2.nextInt();
		for(int i = 0; i < nPlayers; i++) {
			Player.add(new ArrayList<Card>());
		}
	}
	
	public void deal() {
		int i = 0;
		while(i < nPlayers && Deck.getDeck().size() > 0) {
			Player.get(i).add(Deck.getDeck().get(0));
			Deck.getDeck().remove(0);
			
			if(i != (nPlayers-1)) {
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
		for(int i = 0; i < Player.size(); i++) {
			ComparingPile.add(Player.get(i).get(0));
			Player.get(i).remove(0);
		}
	}
/*
 *  Show the detail of the card you drew
 */	
	public void roundDetail() {
		System.out.println("\n\nGame Start\nRound " + nRound + 
				"\nRound " + nRound + ": Players have drawn their cards");

		System.out.println(ComparingPile.get(0));  //print detail of your first card
		System.out.println("There are [" + Player.get(0).size() + "] cards in your deck");
		System.out.println("It is your turn to select a category, the categories are:");
		System.out.println("   1: Size\n   2: Speed\n   3: Range\n   4: Firepower\n   5: Cargo");
		System.out.print("Enter the number for your attribute: ");
	}
	
	public void takeMax() {
		Max = Collections.max(CompValue);                         // Max is an Integer Object
		System.err.println("Max value is: " + Max.toString());    //For test
		for(int i = 0; i < CompValue.size(); i++) {               //CompValue.size() is number of Players
			if(CompValue.get(i) == Max) {
				maxList.add(i);
				System.err.println("Player" + (i+1));             //For test
			}
		}
	}
	
	public void selectAttribute() {
		Scanner a = new Scanner(System.in);
		attribute = a.nextInt();
		
		if(attribute == 1) {
			for(int i = 0; i < nPlayers; i++) {
				CompValue.add(ComparingPile.get(i).getSize());
			}
		}else if(attribute == 2) {
			for(int i = 0; i < 5; i++) {
				CompValue.add(ComparingPile.get(i).getSpeed());
			}			
		}else if(attribute == 3) {
			for(int i = 0; i < 5; i++) {
				CompValue.add(ComparingPile.get(i).getRange());
			}			
		}else if(attribute == 4) {
			for(int i = 0; i < 5; i++) {
				CompValue.add(ComparingPile.get(i).getFirepower());
			}			
		}else if(attribute == 5) {
			for(int i = 0; i < 5; i++) {
				CompValue.add(ComparingPile.get(i).getCargo());
			}
		}
		takeMax();
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
			System.out.println("<TEST> Common Pile:");
			for (int i = 0; i < CommonPile.size(); i++) {
				System.err.println(CommonPile.get(i));
			}
			System.out.println("The winning card was [" 
					+ ComparingPile.get(CompValue.indexOf(Max)).getDescription()
					+ "]:");
			printResult();
			
		} else {
			int playerIndex = CompValue.indexOf(Max);
			System.out.println("Round " + nRound + ": Player" + playerIndex + " won this round");
			System.out.println("The winning card was [" 
					+ ComparingPile.get(playerIndex).getDescription()
					+ "]:");
			printResult();
			for(int i = 0; i < CommonPile.size(); i++) {
				Player.get(playerIndex).add(CommonPile.get(i));
			}
			CommonPile.clear();
		}
		CompValue.clear();
		maxList.clear();
		nRound++;
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
		TestPlayerDecks();          //For test
	}
	
	//Tests:
	
	public void TestPlayerDecks() { //For test
		System.err.println("=======================================");
		for(int i = 0; i < Player.size(); i++) {
			System.err.println("Player" + (i+1) + " Deck:");
			for(int j=0; j < Player.get(i).size();j++) {
				System.err.println(Player.get(i).get(j));//Test if the deck is successfully shuffled
			}
			System.err.println("=======================================");
		}
		System.err.println("Original deck now has [" + Deck.getDeck().size() + "] card left");
	}
	
	public void checkShuffle() {
		if(Deck.getDeck().size()>0) {  //Test if the deck is successfully shuffled
			for(int i=0;i<Deck.getDeck().size();i++) {
				System.out.println(Deck.getDeck().get(i));
			}
		}
	}
}
