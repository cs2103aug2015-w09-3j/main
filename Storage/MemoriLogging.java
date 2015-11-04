//@author A0121262X
package memori.Storage;

import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.logging.FileHandler;

public class MemoriLogging {
	private static FileHandler fh = null;
	private static Logger logger = null;
	private static MemoriLogging INSTANCE = new MemoriLogging();
	public static final String LOG_FILE_NAME = "log.txt";
	public static final String LOG_DIRECTORY = "log/";
	public static final String LOG_DIRECTORY_NAME = "log";

	
	private MemoriLogging() {
		
	}
	
	public static MemoriLogging getInstance(String className) {
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
        .appendPattern("MM").appendLiteral("-")
        .appendPattern("dd").appendLiteral("-")
        .appendPattern("yyyy").appendLiteral("-")
        .appendPattern("hh").appendLiteral("")
        .appendPattern("mm").appendLiteral("")
        .appendPattern("ss").appendLiteral("")
        .appendPattern("a")
        .toFormatter();
		String timestamp = LocalDateTime.now().format(formatter);
		INSTANCE.initializeLog(className, timestamp + LOG_FILE_NAME);
		return INSTANCE;
	}
	
	private void createLogFolder() { 
		File logDirectory = new File(LOG_DIRECTORY_NAME);

		//if the directory does not exist, create it
		if (!logDirectory.exists()) {
		    System.out.println("creating directory: " + LOG_DIRECTORY_NAME);
		    boolean result = false;
	
		    try{
		        logDirectory.mkdir();
		        result = true;
		    } 
		    catch(SecurityException e){
		    	severeLogging(e.getMessage());
		      e.printStackTrace();
		    }
		    
		    if(result) {    
		        System.out.println("Log directory created");  
		    }
		}
	}
	
	private boolean initializeLog(String className, String logFileName) {
		logger = Logger.getLogger(className);
		createLogFolder();
		
		try {		
			fh = new FileHandler(LOG_DIRECTORY + logFileName);
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
	
	public boolean closeLog(){
		try{
			if(fh != null){
				fh.close();
				fh = null;
			}
			return true;
		}catch (Exception e) {
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
