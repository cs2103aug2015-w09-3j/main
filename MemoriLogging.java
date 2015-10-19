package memori;

import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.IOException;
import java.util.logging.FileHandler;

public class MemoriLogging {
	private static FileHandler fh = null;
	private static Logger logger = null;
	private static MemoriLogging INSTANCE = new MemoriLogging();
	public static final String LOG_FILE_NAME = "log.txt";
	
	private MemoriLogging() {
		
	}
	
	public static MemoriLogging getInstance(String className) {
		INSTANCE.initializeLog(className, LOG_FILE_NAME);
		return INSTANCE;
	}
	
	private boolean initializeLog(String className, String logFileName) {
		logger = Logger.getLogger(className);
		
		try {		
			fh = new FileHandler(logFileName);
			logger.addHandler(fh);
			
			SimpleFormatter formatter = new SimpleFormatter(); 
			fh.setFormatter(formatter); 

			return true;
		} catch (SecurityException e) {
			severeLogging(e.getMessage());
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			severeLogging(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	//The highest value; intended for extremely important messages (e.g. fatal program errors).
	public boolean severeLogging(String msg){
		if(logger != null){
			logger.severe(msg);
			return true;
		}
		return false;
	}
	
	//Intended for warning messages.
	public boolean warningLogging(String msg) {
		if(logger != null){
			logger.warning(msg);
			return true;
		}
		return false;
	}
	
	//Informational runtime messages.
	public boolean infoLogging(String msg){
		if(logger != null){
			logger.info(msg);
			return true;
		}
		return false;
	}
	
	//Informational messages about configuration settings/setup.
	public boolean configLogging(String msg){
		if(logger != null){
			logger.config(msg);
			return true;
		}
		return false;
	}
	
	//Used for greater detail, when debugging/diagnosing problems.
	public boolean fineLogging(String msg){
		if(logger != null){
			logger.fine(msg);
			return true;
		}
		return false;
	}
	
	//Even greater detail than fine
	public boolean finerLogging(String msg){
		if(logger != null){
			logger.finer(msg);
			return true;
		}
		return false;
	}
	
	//The lowest value; greatest detail.
	public boolean finestLogging(String msg){
		if(logger != null){
			logger.finest(msg);
			return true;
		}
		return false;
	}
}
