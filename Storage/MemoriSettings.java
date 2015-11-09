//@@author A0121262X
package memori.Storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MemoriSettings {
	private String fileName = "memori.json";
	private String settingsFileName = "settings.json";
	private static final String DEFAULT_FILE_NAME = "memori.json";
	private boolean invertMonth =  true;
	private final static String SETTINGS_LOCATION = "settings.json";
	private static final MemoriSettings INSTANCE = new MemoriSettings();

	private MemoriSettings() {

	}
	
	/**
	 * Singleton pattern to restrict the instantiation of a class to one object.
	 */
	public static MemoriSettings getInstance() {
		return INSTANCE;
	}

	public String getFileName() {
		return this.fileName;
	}
	
	public String setFileName(String name) {
		return this.fileName = name;
	}
	
	public boolean getInvertMonth(){
		return invertMonth;
	}
	
	public void changeFilePath(String name){
		this.fileName = name;
		writeSettingsFile();
	}
	
	public void createSettingsFile(String name){
		this.settingsFileName = name;
		writeSettingsFile();
	}
	
	public void changeInvertMonth(boolean status ){
		invertMonth = status;
	}
	
	/**
	 * Writes attributes to the settings file
	 */
	public void writeSettingsFile(){
		FileHandler fh = new FileHandler();
		Gson gson = new GsonBuilder().serializeNulls()
		 		.setPrettyPrinting()
				.create();
		String userSettings = gson.toJson(this);
		checkValidFilePath(fh, userSettings);
	}
	
	/**
	 * Ensures that the user can only enter a valid file path for memori.json
	 *
	 * @param fh    					fileHandler to write files
	 * @param userSettings		user's preferred settings
	 */
	private void checkValidFilePath(FileHandler fh, String userSettings) {
		if (!fh.writeFile(SETTINGS_LOCATION, userSettings)) {
			fileName = DEFAULT_FILE_NAME;
			writeSettingsFile();
			//System.out.println("rewriting to default location");
		}
	}
	
	/**
	 * loads the settings and converts its JSON objects to Java objects
	 * 
	 * @return     MemoriSettings object
	 * @throws IllegalArgumentException  If zone is <= 0.
	 */
	public static MemoriSettings loadMemoriSettings() {
		FileHandler fh = new FileHandler();
		String settingsJSON= fh.readFile(SETTINGS_LOCATION);
		MemoriSettings ms =new Gson().fromJson(settingsJSON, MemoriSettings.class);
		return ms;
	}
	
	public static void main(String[] args) {
		MemoriSettings ms = new MemoriSettings();
		ms.changeFilePath("memori2.json");
		MemoriSettings ms2 = MemoriSettings.loadMemoriSettings();
		System.out.println(ms2.getFileName());
	}
}
