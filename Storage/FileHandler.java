//@@author A0121262X
package memori.Storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
	private static MemoriLogging memoriLogger = MemoriLogging.getInstance(FileHandler.class.getName());
	private static final FileHandler fileHandlerInstance = new FileHandler();
	
	public static FileHandler getInstance() {
		return fileHandlerInstance;
	}
	
	/**
	 * Return the content of the file by reading the file.
	 *
	 * @param filePath		file path of the file that needs to be read
	 * @return     				content of the file, in String form.
	 */
	public String readFile(String filePath) {
		BufferedReader br = null;
		String output = "";
		try {

			String sCurrentLine;
			File file = new File(filePath);
			if (!file.exists())
				return null;

			br = new BufferedReader(new FileReader(filePath));

			while ((sCurrentLine = br.readLine()) != null) {
				output += sCurrentLine;
			}

		} catch (IOException e) {
			memoriLogger.severeLogging(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				memoriLogger.severeLogging(ex.getMessage());
				ex.printStackTrace();
			}
		}
		return output;
	}

	/**
	 * Writes content into a file, with the file path specified
	 *
	 * @param filePath		file path of the file that requires content to be written
	 * @param content			content to be written into the specified file
	 * @return     				true if file is successfully written
	 */
	public boolean writeFile(String filePath, String content) {
		try {
			File file = new File(filePath);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			return true;
		} catch (IOException e) {
			memoriLogger.severeLogging(e.getMessage());
			return false;
		}
	}

	public static void main(String[] args) {
		FileHandler fh = new FileHandler();
		fh.writeFile("/Users/WenTjun/workspace/Memori/testtest.txt", "aaaa");
		System.out.println(fh.readFile("test.txt"));
	}

}
