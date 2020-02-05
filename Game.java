import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Game {
	private static int nRound = 1;
	private static ArrayList<Integer> maxList = new ArrayList<>();
	private static Object Max;
//	private static int MaxCardValue;
	private static int attribute;
	
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
			Game.Loading();
		}
//		s1.close();
	}
	
	public static void Loading() {
		new Load();              //Load cards
		Deck.shuffleCards();
		Deck.deal();
		Test.PlayerDecks();      //Uncomment for testing
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

		System.out.println(Deck.ComparingPile.get(0));  //print detail of this card
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
			for(int i = 0; i < 5; i++) {
				Deck.Comparing.add(Deck.ComparingPile.get(i).getSize());
			}
//			Deck.Comparing.add(Deck.ComparingPile.get(0).getSize());
//			Deck.Comparing.add(Deck.ComparingPile.get(1).getSize());
//			Deck.Comparing.add(Deck.ComparingPile.get(2).getSize());
//			Deck.Comparing.add(Deck.ComparingPile.get(3).getSize());
//			Deck.Comparing.add(Deck.ComparingPile.get(4).getSize());
			
//			MaxCardValue = Collections.max(Deck.Comparing);
//			System.out.println(MaxCardValue);
			
			Max = Collections.max(Deck.Comparing);
			System.out.println("Max value is: " + Max.toString()); //For test
			for(int i = 0; i < Deck.Comparing.size(); i++) {
				if(Deck.Comparing.get(i) == Max) {
					System.out.println("Player" + (i+1));
					maxList.add(i);
				}
			}
//			//备选方案（只能print第一个找到的值）
//			System.out.println("Player" + (Deck.Comparing.indexOf(Max)+1)); 
//			//Deck.Comparing.indexOf(Max) is an int
//			Deck.ComparingPile.get(PlayerIndex+1).getDescription();
		}else if(attribute == 2) {
			Deck.Comparing.add(Deck.ComparingPile.get(0).getSpeed());
			Deck.Comparing.add(Deck.ComparingPile.get(1).getSpeed());
			Deck.Comparing.add(Deck.ComparingPile.get(2).getSpeed());
			Deck.Comparing.add(Deck.ComparingPile.get(3).getSpeed());
			Deck.Comparing.add(Deck.ComparingPile.get(4).getSpeed());
			
//			MaxCardValue = Collections.max(Deck.Comparing);
//			System.out.println(MaxCardValue);
		}else if(attribute == 3) {
			Deck.Comparing.add(Deck.ComparingPile.get(0).getRange());
			Deck.Comparing.add(Deck.ComparingPile.get(1).getRange());
			Deck.Comparing.add(Deck.ComparingPile.get(2).getRange());
			Deck.Comparing.add(Deck.ComparingPile.get(3).getRange());
			Deck.Comparing.add(Deck.ComparingPile.get(4).getRange());
			
//			MaxCardValue = Collections.max(Deck.Comparing);
//			System.out.println(MaxCardValue);
		}else if(attribute == 4) {
			Deck.Comparing.add(Deck.ComparingPile.get(0).getFirepower());
			Deck.Comparing.add(Deck.ComparingPile.get(1).getFirepower());
			Deck.Comparing.add(Deck.ComparingPile.get(2).getFirepower());
			Deck.Comparing.add(Deck.ComparingPile.get(3).getFirepower());
			Deck.Comparing.add(Deck.ComparingPile.get(4).getFirepower());
			
//			MaxCardValue = Collections.max(Deck.Comparing);
//			System.out.println(MaxCardValue);
		}else if(attribute == 5) {
			Deck.Comparing.add(Deck.ComparingPile.get(0).getCargo());
			Deck.Comparing.add(Deck.ComparingPile.get(1).getCargo());
			Deck.Comparing.add(Deck.ComparingPile.get(2).getCargo());
			Deck.Comparing.add(Deck.ComparingPile.get(3).getCargo());
			Deck.Comparing.add(Deck.ComparingPile.get(4).getCargo());
			
//			MaxCardValue = Collections.max(Deck.Comparing);
//			System.out.println(MaxCardValue);
		}
		
		if(maxList.size() > 1) {
			System.out.println("Round " + nRound + ": This round was a Draw, "
					+ "common pile now has " + Deck.Comparing.size() 
					+ " cards\nThe winning card was [" 
					+ Deck.ComparingPile.get(Deck.Comparing.indexOf(Max)).getDescription() 
					+ "]:");
			if(attribute == 1) {
				System.out.println("   > Size:       " 
			+ Deck.ComparingPile.get(Deck.Comparing.indexOf(Max)).getSize() + " <--");
				System.out.println("   > Speed:      " 
			+ Deck.ComparingPile.get(Deck.Comparing.indexOf(Max)).getSpeed());
				System.out.println("   > Range:      " 
			+ Deck.ComparingPile.get(Deck.Comparing.indexOf(Max)).getRange());
				System.out.println("   > Firepower:  " 
			+ Deck.ComparingPile.get(Deck.Comparing.indexOf(Max)).getFirepower());
				System.out.println("   > Cargo:      " 
			+ Deck.ComparingPile.get(Deck.Comparing.indexOf(Max)).getCargo());
			}
		}
		
//		a.close();
		
//		Deck.ComparingPile.get(0).getSize(); //int
		
		
		
		
//		if(PlayerIndex == -1) {
//			System.out.println("Player1");
//		}else if(PlayerIndex == 0) {
//			System.out.println("Player2");
//		}else if(PlayerIndex == 1) {
//			System.out.println("Player3");
//		}else if(PlayerIndex == 2) {
//			System.out.println("Player4");
//		}else if(PlayerIndex == 3) {
//			System.out.println("Player5");
//		}
		//瑕疵：同样的两张卡，只有第一张出现
		//永远== -1 出错
		//最大值找到，但玩家出错
//		System.out.println(Deck.ComparingPile.indexOf(Collections.max(Deck.Comparing)));
	}
}
