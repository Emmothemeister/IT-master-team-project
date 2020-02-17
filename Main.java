
public class Main {
	public static void main(String[] args) {
		System.out.println("--------------------");
		System.out.println("--- Top Trumps   ---");
		System.out.println("--------------------");

//		//First time need these two methods:
//		JDBCUtils.createTables(); 
//		JDBCUtils.insertPLAYER();
		
//		JDBCUtils.resetONEGAME(); //clear data
		
		boolean writeGameLogsToFile = false; // Should we write game logs to file?
//		if (args[0].equalsIgnoreCase("true"))
		writeGameLogsToFile=true; // Command line selection
		
				
		new Game(writeGameLogsToFile); 

	}	
}
