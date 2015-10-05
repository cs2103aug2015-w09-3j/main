package memori;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Storage {
	String fileContents ="";
	MemoriSettings ms;
	FileHandler fh = new FileHandler();
	
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
			ms = new MemoriSettings();
		}
		return ms;
	}
}
