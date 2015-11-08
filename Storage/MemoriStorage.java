//@@author A0121262X
package memori.Storage;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import memori.googleSync.SyncObjectQueue;
import memori.logic.MemoriCalendar;

public class MemoriStorage {
	private static final String LOG_STORAGE_LOAD_SUCCESS = "Successfully loaded JSON file";
	private static final String LOG_STORAGE_SAVE_SUCCESS = "Successfully saved JSON file";
	private static final String LOG_SETTINGS_LOAD_SUCCESS = "Successfully loaded settings file";
	private static final String SETTINGS_FILE_NAME = "settings.json";
	private static final String STORAGE_FILE_NAME = "memori.json";
	private static final String QUEUE_FILE_NAME = "queue.json";
	private static final MemoriStorage storageInstance = new MemoriStorage();
	String fileContents = "";
	MemoriSettings ms;
	FileHandler fh = new FileHandler();
	private static MemoriLogging memoriLogger = null;

	private MemoriStorage() {

	}

	/**
	 * Singleton pattern to restrict the instantiation of a class to one object.
	 */
	public static MemoriStorage getInstance() {
		if (memoriLogger == null) {
			storageInstance.initializeLog();
		}

		return storageInstance;
	}
	
	/**
	 * Initializes logging for MemoriStorage.java
	 * 
	 * @return		true if logging is successfully initialized.
	 */
	public boolean initializeLog() {
		try {
			memoriLogger = MemoriLogging.getInstance(MemoriStorage.class.getName());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * retrieves content of storage file
	 */
	private void readFile() {
		fileContents = fh.readFile(ms.getFileName());
	}

	/**
	 * loads the storage file by converting JSON objects in memori.json 
	 * to Java objects
	 *
	 * @return     Java objects from their JSON representation in the storage file
	 */
	public MemoriCalendar loadCalendar() {
		readFile();
		memoriLogger.infoLogging(LOG_STORAGE_LOAD_SUCCESS);
		return new Gson().fromJson(fileContents, MemoriCalendar.class);
	}
	
	/**
	 * Saves content of the calendar into storage by converting Java objects
	 * into their JSON representation
	 *
	 * @param memoriCalendar	Calendar object to be saved
	 */
	public void saveCalendar(MemoriCalendar memoriCalendar) {
		FileHandler fh = new FileHandler();
		Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
		String fileContent = gson.toJson(memoriCalendar);
		if (!fh.writeFile(ms.getFileName(), fileContent)) {
			ms.setFileName(STORAGE_FILE_NAME);
			ms.writeSettingsFile();
		}
		memoriLogger.infoLogging(LOG_STORAGE_SAVE_SUCCESS);
	}
	
	/**
	 * Saving for sync to Google Calendar
	 *
	 * @param toGoogle		Java object to be synced to Google Calendar
	 */
	public void saveQueue(SyncObjectQueue toGoogle){
		FileHandler fh = new FileHandler();
		Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
		String fileContent = gson.toJson(toGoogle);
		fh.writeFile(QUEUE_FILE_NAME, fileContent);
		memoriLogger.infoLogging(LOG_STORAGE_SAVE_SUCCESS);
	}
	
	/**
	 * creates settings file if settings file is deleted.
	 *
	 * @return     an instance of MemoriSettings
	 * @throws IllegalArgumentException  If zone is <= 0.
	 */
	public MemoriSettings loadSettings() {
		ms = MemoriSettings.loadMemoriSettings();
		if (ms == null) {
			ms = MemoriSettings.getInstance();
			ms.createSettingsFile(SETTINGS_FILE_NAME);
		}
		memoriLogger.infoLogging(LOG_SETTINGS_LOAD_SUCCESS);
		return ms;
	}
	
	/**
	 * loads from Google Calendar. supports 2-way sync.
	 * 
	 * @return     Java object from its JSON representation in queue.json.
	 */
	public SyncObjectQueue loadQueue() {
		FileHandler fh = new FileHandler();
		String queueContents = fh.readFile(QUEUE_FILE_NAME);
		return new Gson().fromJson(queueContents, SyncObjectQueue.class);
	}
}
