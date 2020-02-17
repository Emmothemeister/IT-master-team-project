package commandline;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class TestLog {
	
	public static void record(String s) {
		try {
			InputStream is = TestLog.class.getClassLoader().getResourceAsStream("logging.properties");
			//Log File storing location can be changed inside "logging.properties" file
			//"logging.properties" is located in "resources" 
			LogManager lm = LogManager.getLogManager();
			Logger logger = Logger.getLogger("statistic.testLog");
			
			lm.readConfiguration(is);
			logger.info(s);
			logger.info("--------------------------------------------");
			
		}catch (SecurityException se){
			se.printStackTrace();
		}catch (IOException ie){
			ie.printStackTrace();
		}		
	}

}
