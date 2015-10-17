package memori;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Storage {
	private static final Storage INSTANCE = new Storage();
	String fileContents ="";
	MemoriSettings ms;
	FileHandler fh = new FileHandler();
	
	private Storage() {
		
	}
	
	public static Storage getInstance() {
		return INSTANCE;
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
		return new Gson().fromJson(fileContents, MemoriCalendar.class);
	}
	
	public void saveCalendar(MemoriCalendar memoriCalendar){
		FileHandler fh = new FileHandler();
	
		Gson gson = new GsonBuilder().serializeNulls()
		 		.setPrettyPrinting()
				.create();
		String fileContent = gson.toJson(memoriCalendar);
		fh.writeFile(ms.getFileName(), fileContent);
	}
	
	public MemoriSettings loadSettings() {
		ms = MemoriSettings.loadMemoriSettings();
		if(ms == null){
			ms = ms.getInstance();
			ms.createSettingsFile("settings.json");
		}
		return ms;
	}
}
