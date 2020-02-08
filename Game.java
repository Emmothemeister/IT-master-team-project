import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Game {
	private static int nRound = 1;
	private static ArrayList<Integer> maxList = new ArrayList<>();
	private static Object Max;
	private static int attribute;
	private static Card MaxCard;
	
//	private static int nPlayers;                   //trail for editing player number
//	private static ArrayList<ArrayList<Card>> Player;
	
//	public static void createPlayers(int n) {
//		for(int i = 0; i < n; i++) {
//			Player.add(new ArrayList<Card>());
//		}
//		for(int i = 0; i < Load.card.size(); i++) {
//			for(int j = 0; j < n ; j++) {
//				Player.get(j).add(Load.card.get(0));
//			}
//		}
//	}
	
    public static void Title() {
		System.out.println("--------------------");
		System.out.println("--- Top Trumps   ---");
		System.out.println("--------------------");
	}
	
	public static void Start() {
		System.out.println("Do you want to see past results or play a game?");
		System.out.println("   1: Print Game Statistics");
		System.out.println("   2: Play game");
		System.out.print("Enter the number for your selection: ");
		
		Scanner s1 = new Scanner(System.in);
		int selection = s1.nextInt();
		
		if (selection == 1) {
//			Print Game Statistics
		} else if (selection == 2) {
//			System.out.print("Choose Number of Players [2-5]");
//			Scanner s2 = new Scanner(System.in);
//			nPlayers = s2.nextInt();
//			Loading();
//			createPlayers(nPlayers);
			new Load();              //Load cards
		}
	}
	
/*
 *      Put first card of every player into comparing pile
 */
	public static void draw() {  //draw the first card and put them into ComparingPile
		Deck.ComparingPile.add(Deck.Player1.get(0));
		Deck.Player1.remove(0);
		Deck.ComparingPile.add(Deck.Player2.get(0));
		Deck.Player2.remove(0);
		Deck.ComparingPile.add(Deck.Player3.get(0));
		Deck.Player3.remove(0);
		Deck.ComparingPile.add(Deck.Player4.get(0));
		Deck.Player4.remove(0);
		Deck.ComparingPile.add(Deck.Player5.get(0));
		Deck.Player5.remove(0);
	}
	
	public static void roundDetail() {  //show the detail of the card you drew
		System.out.println("\n\nGame Start\nRound " + nRound + 
				"\nRound " + nRound + ": Players have drawn their cards");

		System.out.println(Deck.ComparingPile.get(0));  //print detail of your first card
		System.out.println("There are [" + Deck.Player1.size() + "] cards in your deck");
		System.out.println("It is your turn to select a category, the categories are:");
		System.out.println("   1: Size\n   2: Speed\n   "
				+ "3: Range\n   4: Firepower\n   5: Cargo");
		System.out.print("Enter the number for your attribute: ");
	}
	
	public static void selectAttribute() {
		Scanner a = new Scanner(System.in);
		attribute = a.nextInt();
		
		if(attribute == 1) {
			for(int i = 0; i < 5; i++) {  //i < 5, 5 is number of players
				Deck.CompValue.add(Deck.ComparingPile.get(i).getSize());
			}
			
			Max = Collections.max(Deck.CompValue); // Max is an Integer Object
			System.err.println("Max value is: " + Max.toString()); //For test
			for(int j = 0; j < Deck.CompValue.size(); j++) { //CompValue.size() is number of Players
				if(Deck.CompValue.get(j) == Max) {
					System.err.println("Player" + (j+1));
					maxList.add(j);
				}
			}
//			//Deck.Comparing.indexOf(Max) is an int

		}else if(attribute == 2) {
			for(int i = 0; i < 5; i++) {
				Deck.CompValue.add(Deck.ComparingPile.get(i).getSpeed());
			}
			Max = Collections.max(Deck.CompValue);
			System.err.println("Max value is: " + Max.toString()); //For test
			for(int j = 0; j < Deck.CompValue.size(); j++) {
				if(Deck.CompValue.get(j) == Max) {
					System.err.println("Player" + (j+1));
					maxList.add(j);
				}
			}
			
		}else if(attribute == 3) {
			for(int i = 0; i < 5; i++) {
				Deck.CompValue.add(Deck.ComparingPile.get(i).getRange());
			}
			Max = Collections.max(Deck.CompValue);
			System.err.println("Max value is: " + Max.toString()); //For test
			for(int j = 0; j < Deck.CompValue.size(); j++) {
				if(Deck.CompValue.get(j) == Max) {
					System.err.println("Player" + (j+1));
					maxList.add(j);
				}
			}
			
		}else if(attribute == 4) {
			for(int i = 0; i < 5; i++) {
				Deck.CompValue.add(Deck.ComparingPile.get(i).getFirepower());
			}
			Max = Collections.max(Deck.CompValue);
			System.err.println("Max value is: " + Max.toString()); //For test
			for(int j = 0; j < Deck.CompValue.size(); j++) {
				if(Deck.CompValue.get(j) == Max) {
					System.err.println("Player" + (j+1));
					maxList.add(j);
				}
			}
			
		}else if(attribute == 5) {
			for(int i = 0; i < 5; i++) {
				Deck.CompValue.add(Deck.ComparingPile.get(i).getCargo());
			}
			Max = Collections.max(Deck.CompValue);
			System.err.println("Max value is: " + Max.toString()); //For test
			for(int j = 0; j < Deck.CompValue.size(); j++) {
				if(Deck.CompValue.get(j) == Max) {
					System.err.println("Player" + (j+1));
					maxList.add(j);
				}
			}
		}
	}
	
	public static void addToCommonPile() {
		for (int i = 0; i < Deck.ComparingPile.size(); i++) {
			Deck.CommonPile.add(Deck.ComparingPile.get(i));
		}
	}
	
	public static void roundResult() {
		addToCommonPile(); //add to common pile
		if(maxList.size() > 1) { //it's a draw			
			System.out.println("Round " + nRound + ": This round was a Draw, "
					+ "common pile now has [" + Deck.CommonPile.size() 
					+ "] cards\nThe winning card was [" 
					+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getDescription() 
					+ "]:");
			
			//print common pile for test
			System.out.println("<TEST> Common Pile:");
			for (int i = 0; i < Deck.CommonPile.size(); i++) {
				System.err.println(Deck.CommonPile.get(i));
			}
			System.out.println("The winning card was [" 
					+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getDescription()
					+ "]:");
			printResult();
			
		} else {
			if(Deck.CompValue.indexOf(Max) == 0) {
//				Player1 You
				System.out.println("Round " + nRound + ": Player You won this round");
				System.out.println("The winning card was [" 
						+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getDescription()
						+ "]:");
				printResult();
				for(int i = 0; i < Deck.CommonPile.size(); i++) {
					Deck.Player1.add(Deck.CommonPile.get(i));
				}
				Deck.CommonPile.clear();
			}else if(Deck.CompValue.indexOf(Max) == 1) {
				System.out.println("Round " + nRound + ": Player 2 won this round");
				System.out.println("The winning card was [" 
						+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getDescription()
						+ "]:");
				printResult();
				for(int i = 0; i < Deck.CommonPile.size(); i++) {
					Deck.Player2.add(Deck.CommonPile.get(i));				
				}
				Deck.CommonPile.clear();
			}else if(Deck.CompValue.indexOf(Max) == 2) {
				System.out.println("Round " + nRound + ": Player 3 won this round");
				System.out.println("The winning card was [" 
						+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getDescription()
						+ "]:");
				printResult();
				for(int i = 0; i < Deck.CommonPile.size(); i++) {
					Deck.Player3.add(Deck.CommonPile.get(i));					
				}
				Deck.CommonPile.clear();
			}else if(Deck.CompValue.indexOf(Max) == 3) {
				System.out.println("Round " + nRound + ": Player 4 won this round");
				System.out.println("The winning card was [" 
						+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getDescription()
						+ "]:");
				printResult();
				for(int i = 0; i < Deck.CommonPile.size(); i++) {
					Deck.Player4.add(Deck.CommonPile.get(i));					
				}
				Deck.CommonPile.clear();
			}else if(Deck.CompValue.indexOf(Max) == 4) {
				System.out.println("Round " + nRound + ": Player 5 won this round");
				System.out.println("The winning card was [" 
						+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getDescription()
						+ "]:");
				printResult();
				for(int i = 0; i < Deck.CommonPile.size(); i++) {
					Deck.Player5.add(Deck.CommonPile.get(i));				
				}
				Deck.CommonPile.clear();
			}
		}
		Deck.CompValue.clear();
		maxList.clear();
		nRound++;
	}
		
	public static void printResult() {
		if(attribute == 1) {
			System.out.println("   > Size:       " 
		+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getSize() + " <--");
			System.out.println("   > Speed:      " 
		+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getSpeed());
			System.out.println("   > Range:      " 
		+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getRange());
			System.out.println("   > Firepower:  " 
		+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getFirepower());
			System.out.println("   > Cargo:      " 
		+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getCargo());
		} else if (attribute == 2) {
			System.out.println("   > Size:       " 
		+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getSize());
			System.out.println("   > Speed:      " 
		+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getSpeed() + " <--");
			System.out.println("   > Range:      " 
		+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getRange());
			System.out.println("   > Firepower:  " 
		+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getFirepower());
			System.out.println("   > Cargo:      " 
		+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getCargo());
		} else if (attribute == 3) {
			System.out.println("   > Size:       " 
		+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getSize());
			System.out.println("   > Speed:      " 
		+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getSpeed());
			System.out.println("   > Range:      " 
		+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getRange() + " <--");
			System.out.println("   > Firepower:  " 
		+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getFirepower());
			System.out.println("   > Cargo:      " 
		+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getCargo());
		} else if (attribute == 4) {
			System.out.println("   > Size:       " 
		+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getSize());
			System.out.println("   > Speed:      " 
		+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getSpeed());
			System.out.println("   > Range:      " 
		+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getRange());
			System.out.println("   > Firepower:  " 
		+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getFirepower() + " <--");
			System.out.println("   > Cargo:      " 
		+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getCargo());
		} else if (attribute == 5) {
			System.out.println("   > Size:       " 
		+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getSize());
			System.out.println("   > Speed:      " 
		+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getSpeed());
			System.out.println("   > Range:      " 
		+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getRange());
			System.out.println("   > Firepower:  " 
		+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getFirepower());
			System.out.println("   > Cargo:      " 
		+ Deck.ComparingPile.get(Deck.CompValue.indexOf(Max)).getCargo() + " <--");
		}
		Deck.ComparingPile.clear();
	}	
}
