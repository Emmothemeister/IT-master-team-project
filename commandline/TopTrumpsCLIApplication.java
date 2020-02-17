package commandline;

import java.util.Scanner;

/**
 * Top Trumps command line application
 */
public class TopTrumpsCLIApplication {

	/**
	 * This main method is called by TopTrumps.java when the user specifies that they want to run in
	 * command line mode. The contents of args[0] is whether we should write game logs to a file.
 	 * @param args
	 *  
	 */
	public static void main(String[] args) {
		

		boolean writeGameLogsToFile = false; // Should we write game logs to file?
		if (args[0].equalsIgnoreCase("true")) writeGameLogsToFile=true; // Command line selection
		
		// State
		boolean userWantsToQuit = false; // flag to check whether the user wants to quit the application
		
		// Loop until the user wants to exit the game
		while (!userWantsToQuit) {

			// ----------------------------------------------------
			// Add your game logic here based on the requirements
			// ----------------------------------------------------
			
            Game TopTrumps = new Game(writeGameLogsToFile); 
			
			boolean correctInput = false;
			while (!correctInput) {
				System.out.println("Would you like to play a new game?");
				System.out.println("   1. Play");
				System.out.println("   2. Quit");
				Scanner s = new Scanner(System.in);
				int playOrNot = s.nextInt();
				if(playOrNot < 1 || playOrNot > 2) {
					correctInput = false;
					System.out.println("\n< Please input a correct number >");
				}else {
					correctInput = true;
					if (playOrNot != 1) {
						userWantsToQuit=true; // use this when the user wants to exit the game
						TopTrumps = null;
					}
				}	
			}
		}


	}

}
