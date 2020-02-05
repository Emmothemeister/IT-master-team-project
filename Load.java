import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Load {	
	static ArrayList<Card> card = new ArrayList<>();

	//Constructor
	public Load() {
		FileReader fr = null;
		try {
			String StarCitizenDeck = "/Users/joshua/Desktop/StarCitizenDeck.txt";
			fr = new FileReader(StarCitizenDeck);
			Scanner s = new Scanner(fr);
			// Loop
			int lineNumber = 0;
			//Attention card size
			while(lineNumber<=40) {  
				if(lineNumber == 0) {
					s.nextLine();
					lineNumber++;
					continue;
				} else {
					// get the next line
					String line = s.nextLine();
					String[] tokens = line.split(" ");
					String Description = tokens[0];
					// convert into integer
					int Size = Integer.parseInt(tokens[1]);
					// make a new card object and insert into array
					int Speed = Integer.parseInt(tokens[2]);
					int Range = Integer.parseInt(tokens[3]);
					int Firepower = Integer.parseInt(tokens[4]);
					int Cargo = Integer.parseInt(tokens[5]);
					
					card.add(new Card(Description,Size,Speed,Range,Firepower,Cargo));
					lineNumber++;
				}				
			}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(fr!=null) {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
/*
 *      Test if the cards are successfully loaded
 *      A toString Method in Card class need to be uncommented
 */
		
		if(card.size()>0) {
			for(int i=0;i<card.size();i++) {
				System.err.println(card.get(i));
			}
		} else {
			System.err.println("No cards detected");
		}
		
		
	}
}
