package commandline;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
/*
 *  This class is only used for reading file and creating Card objects,
 *  and then adding them into deck
 */
public class Load {
	
	public Load(Deck d, boolean writeLogs) {   //Constructor
		FileReader fr = null;
		try {
			String StarCitizenDeck = "/Users/joshua/Desktop/StarCitizenDeck.txt";
			fr = new FileReader(StarCitizenDeck);
			Scanner s = new Scanner(fr);
			// Loop
			int lineNumber = 0;
			//Attention card size
			
			while(s.hasNextLine()) {
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
					
					//add cards into deck
					d.getDeck().add(new Card(Description,Size,Speed,Range,Firepower,Cargo));
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
 *      Write Test log to test if the cards are successfully loaded
 *      
 */
		if(writeLogs) {
			if(d.getDeck().size() > 0) {
				String cardList = "";
				String cl = "";
				
				for(int i = 0; i < d.getDeck().size();i++) {
					cl = d.getDeck().get(i).testLog() + "\n";
					cardList += cl;
				}
				TestLog.record("Cards are loaded as follows:");
				TestLog.record(cardList);
			}
		}
				
	}
}
