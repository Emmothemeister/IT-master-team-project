package statistic;

import java.io.InputStream;
import java.util.logging.*;
import org.junit.Test;

public class testLog {
	static InputStream is = testLog.class.getClassLoader().getResourceAsStream("logging.properties");
	static LogManager lm = LogManager.getLogManager();
	static Logger logger = Logger.getLogger("statistic.testLog");
	static String dashLine = "------------------------------------------------------------------------------------------";
	static String data = "¿¨×éÐÅÏ¢";
	@Test
	public void test() throws Exception {
		lm.readConfiguration(is);
		logger.info(data);
		logger.info(dashLine);
	}
}
