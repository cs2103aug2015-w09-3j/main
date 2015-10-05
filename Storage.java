package memori;
// sample
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Storage {
	private File file = new File("memori.json");
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
		return ms = MemoriSettings.loadMemoriSettings();
	}
}
