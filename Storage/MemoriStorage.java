//@@author A0121262X
package memori.Storage;

import java.util.LinkedList;
import java.util.Queue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import memori.googleSync.SyncObject;
import memori.googleSync.SyncObjectQueue;
import memori.logic.MemoriCalendar;

public class MemoriStorage {
	private static final String LOG_STORAGE_LOAD_SUCCESS = "Successfully loaded JSON file";
	private static final String LOG_STORAGE_SAVE_SUCCESS = "Successfully saved JSON file";
	private static final String LOG_SETTINGS_LOAD_SUCCESS = "Successfully loaded settings file";
	private static final String LOG_QUEUE_SAVE_SUCCESS = "Successfully saved google Queue file";
	private static final String LOG_QUEUE_LOAD_SUCCESS = "Successfully loaded google Queue file";
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

	public static MemoriStorage getInstance() {
		if (memoriLogger == null) {
			storageInstance.initializeLog();
		}

		return storageInstance;
	}

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
	 * public void printSampleMessage() { System.out.println("blank fire"); }
	 **/
	private void readFile() {
		fileContents = fh.readFile(ms.getFileName());
	}

	public MemoriCalendar loadCalendar() {

		readFile();
		memoriLogger.infoLogging(LOG_STORAGE_LOAD_SUCCESS);
		return new Gson().fromJson(fileContents, MemoriCalendar.class);
	}

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
	
	public void saveQueue(SyncObjectQueue toGoogle){
		FileHandler fh = new FileHandler();
		Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
		String fileContent = gson.toJson(toGoogle);
		fh.writeFile(QUEUE_FILE_NAME, fileContent);
		memoriLogger.infoLogging(LOG_STORAGE_SAVE_SUCCESS);
	}

	public MemoriSettings loadSettings() {
		ms = MemoriSettings.loadMemoriSettings();
		if (ms == null) {
			ms = MemoriSettings.getInstance();
			ms.createSettingsFile(SETTINGS_FILE_NAME);
		}
		memoriLogger.infoLogging(LOG_SETTINGS_LOAD_SUCCESS);
		return ms;
	}
	
	public SyncObjectQueue loadQueue() {
		FileHandler fh = new FileHandler();
		String queueContents = fh.readFile(QUEUE_FILE_NAME);
		return new Gson().fromJson(queueContents, SyncObjectQueue.class);
	}
}