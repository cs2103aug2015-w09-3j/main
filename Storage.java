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
	
	private void readFile() {
		try {
			String line;
			BufferedReader br = new BufferedReader(new FileReader(file));
			line = br.readLine();
			while (line != null) {
				fileContents += line;
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		fh.writeFile("memori.json", fileContent);
	}
}
