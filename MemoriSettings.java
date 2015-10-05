package memori;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MemoriSettings {
	private String fileName = "memori.json";
	private final static String SETTINGS_LOCATION = "settings.json";

	public MemoriSettings() {
		writeSettingsFile();
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	public void changeFilePath(String name){
		this.fileName = name;
		writeSettingsFile();
		
	}
	private void writeSettingsFile(){
		FileHandler fh = new FileHandler();
		Gson gson = new GsonBuilder().serializeNulls()
		 		.setPrettyPrinting()
				.create();
		String userSettings = gson.toJson(this);
		fh.writeFile(SETTINGS_LOCATION, userSettings);
	}
	public static MemoriSettings loadMemoriSettings() {
		FileHandler fh = new FileHandler();
		String settingsJSON= fh.readFile(SETTINGS_LOCATION);
		return new Gson().fromJson(settingsJSON, MemoriSettings.class);
	}
	
	public static void main(String[] args) {
		MemoriSettings ms = new MemoriSettings();
		ms.changeFilePath("memori2.json");
		MemoriSettings ms2 = MemoriSettings.loadMemoriSettings();
		System.out.println(ms2.getFileName());
	}
	
	/**
	protected void createNewDirectory() {
		File directory = new File(searchUserDocumentDirectory() + FOLDERNAME);
		// if the directory does not exist, create it
		if (!directory.exists()) {
			System.out.println("creating directory: ");
			boolean result = directory.mkdir();
			if (result) {
				System.out.println("DIR created");
			}
		}
	}
	
	protected String searchUserDocumentDirectory() {
		return System.getProperty("user.home") + "/Documents/";
	}
	**/
}
