//@@author A0121262X
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
	
	/**
	 * Singleton pattern to restrict the instantiation of a class to one object.
	 * Logging files are created based on timestamp. 
	 */
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
	
	/**
	 * Creates a folder to store logging files
	 *
	 * @throws IllegalArgumentException  If zone is <= 0.
	 */
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
	
	/**
	 * Initializes the logging operation.
	 *
	 * @param className			 name of the class the requires logging	
	 * @param logFileName    name of logging file
	 * @return     					 true if logging is successfully initialized
	 */
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
	
	/**
	 * Stops the logging operation
	 *
	 * @return			true if logging successfully stops
	 */
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
	
	/**
	 * intended for events that are of considerable importance 
	 * and which will prevent normal program execution.
	 *
	 * @param msg			 messaged to be logged
	 * @return     		 true if the messaged is successfully logged
	 */
	public boolean severeLogging(String msg){
		if(logger != null){
			logger.severe(msg);
			return true;
		}
		return false;
	}
	
	/**
	 * intended for events that will be of interest to end users or system managers, 
	 * or which indicate potential problems.
	 * 
	 * @param msg			 messaged to be logged
	 * @return     		 true if the messaged is successfully logged
	 */
	public boolean warningLogging(String msg) {
		if(logger != null){
			logger.warning(msg);
			return true;
		}
		return false;
	}
	
	/**
	 * intended for reasonably significant messages that will make sense to end users 
	 * and system administrators
	 * 
	 * @param msg			 messaged to be logged
	 * @return     		 true if the messaged is successfully logged
	 */
	public boolean infoLogging(String msg){
		if(logger != null){
			logger.info(msg);
			return true;
		}
		return false;
	}
	
	/**
	 * intended to provide a variety of static configuration information, to assist in 
	 * debugging problems that may be associated with particular configurations.
	 * 
	 * @param msg			 messaged to be logged
	 * @return     		 true if the messaged is successfully logged
	 */
	public boolean configLogging(String msg){
		if(logger != null){
			logger.config(msg);
			return true;
		}
		return false;
	}
	
	/**
	 * information that will be broadly interesting to developers who do not have a 
	 * specialized interest in the specific subsystem.
	 * 
	 * @param msg			 messaged to be logged
	 * @return     		 true if the messaged is successfully logged
	 */
	public boolean fineLogging(String msg){
		if(logger != null){
			logger.fine(msg);
			return true;
		}
		return false;
	}
	
	/**
	 * fairly detailed tracing message. By default logging calls for entering, 
	 * returning, or throwing an exception are traced at this level.
	 * 
	 * @param msg			 messaged to be logged
	 * @return     		 true if the messaged is successfully logged
	 */
	//Even greater detail than fine
	public boolean finerLogging(String msg){
		if(logger != null){
			logger.finer(msg);
			return true;
		}
		return false;
	}
	
	/**
	 * logging of highest level,
	 * highly detailed tracing message.
	 * 
	 * @param msg			 messaged to be logged
	 * @return     		 true if the messaged is successfully logged
	 */
	//The lowest value; greatest detail.
	public boolean finestLogging(String msg){
		if(logger != null){
			logger.finest(msg);
			return true;
		}
		return false;
	}
}
