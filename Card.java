
public class Card {
	private String Description;
	private int Size;
	private int Speed;
	private int Range;
	private int Firepower;
	private int Cargo;
	
	public Card(String D,int Sz,int Sd,int R,int F,int C) {
		Description = D;
		Size = Sz;
		Speed = Sd;
		Range = R;
		Firepower = F;
		Cargo = C;
	}
	
/*
 *  Test if the cards are successfully loaded
 *  This method prints the table of all the cards
 */
	
	public String toString() {
		String cardDetals = String.format("%-15s %2d   %2d   %2d   %2d   %2d",
				Description,Size,Speed,Range,Firepower,Cargo);
		return cardDetals;
	}
	
//	public String toString() {
//		String cardName = "You drew '" + Description + "':\n";
//		String cardDetail = "   > Size:       " + Size + "\n" +
//				"   > Speed:      " + Speed + "\n" +
//				"   > Range:      " + Range + "\n" +
//				"   > Firepower:  " + Firepower + "\n" +
//				"   > Cargo:      " + Cargo;
//		return cardName + cardDetail;
//	}
	
	//Getters
	
	public String getDescription() {
		return Description;
	}
	public int getSize() {
		return Size;
	}
	public int getSpeed() {
		return Speed;
	}
	public int getRange() {
		return Range;
	}
	public int getFirepower() {
		return Firepower;
	}
	public int getCargo() {
		return Cargo;
	}
}
