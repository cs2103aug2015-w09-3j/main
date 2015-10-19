package memori;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Storage {
	private static final String LOG_STORAGE_LOAD_SUCCESS = "Successfully loaded JSON file";
	private static final String LOG_STORAGE_SAVE_SUCCESS = "Successfully saved JSON file";
	private static final String LOG_SETTINGS_LOAD_SUCCESS = "Successfully loaded settings file";
	private static final Storage storageInstance = new Storage();
	String fileContents ="";
	MemoriSettings ms;
	FileHandler fh = new FileHandler();
	private static MemoriLogging memoriLogger = null;
	
	private Storage() {
		
	}
	
	public static Storage getInstance() {
		if (memoriLogger == null) {
			storageInstance.initializeLog();
		}
		
		return storageInstance;
	}
	
	public boolean initializeLog() {
		try {
			memoriLogger = MemoriLogging.getInstance(Storage.class.getName());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	public void printSampleMessage() {
    System.out.println("blank fire");
  }
	**/
	private void readFile() {
		fileContents = fh.readFile(ms.getFileName());
	}
	
	public MemoriCalendar loadCalendar(){
		readFile();
		memoriLogger.infoLogging(LOG_STORAGE_LOAD_SUCCESS);
		return new Gson().fromJson(fileContents, MemoriCalendar.class);
	}
	
	public void saveCalendar(MemoriCalendar memoriCalendar){
		FileHandler fh = new FileHandler();
	
		Gson gson = new GsonBuilder().serializeNulls()
		 		.setPrettyPrinting()
				.create();
		String fileContent = gson.toJson(memoriCalendar);
		fh.writeFile(ms.getFileName(), fileContent);
		memoriLogger.infoLogging(LOG_STORAGE_SAVE_SUCCESS);
	}
	
	public MemoriSettings loadSettings() {
		ms = MemoriSettings.loadMemoriSettings();
		if(ms == null){
			ms = ms.getInstance();
			ms.createSettingsFile("settings.json");
		}
		memoriLogger.infoLogging(LOG_SETTINGS_LOAD_SUCCESS);
		return ms;
	}
}
