//@@author A0121262X
package memori.Storage;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

import memori.logic.MemoriCalendar;

public class SettingsTest {
	String defaultSettingsFileName = "settings.json";
	String defaultStorageFileName = "memori.json";
	String duplicatedDefaultStorageFileName = "memori2.json";
	String ValidStorageFolderName = "testStorageLocation";
	String validStorageFilePath = "testStorageLocation/memori.json";
	String invalidStorageFilePath = "wrongStorageLocation/memori.json";
	File defaultSettingsTestFile = new File(defaultSettingsFileName);
	File defaultStorageTestFile = new File(defaultStorageFileName);
	File validTestStorageFolder = new File(ValidStorageFolderName);
	File validStorageTestPath = new File(ValidStorageFolderName + "/" + defaultStorageFileName);
	private MemoriCalendar testMemoriCalendar;

	/**
	 * tests if settings file is recreated in the event the settings file is deleted
	 */
	@Test
	public void testWriteSettingsFile() {
		defaultSettingsTestFile.delete();
		
		assertEquals(false,defaultSettingsTestFile.exists());

		MemoriSettings testSettings = MemoriSettings.getInstance();
		testSettings.writeSettingsFile();
		
		assertEquals(true, defaultSettingsTestFile.exists());
	}
	
	/**
	 * tests if changeFilePath prevents users 
	 * from changing storage file path to one which is illegal
	 */
	@Test
	public void testChangeStorageFileLocation() {
		duplicateStoragefile();
		defaultStorageTestFile.delete();
		
		//first, test by changing file path to valid file path (target directory exists)
		MemoriSettings testSettings = MemoriSettings.getInstance();
		MemoriStorage testStorage = MemoriStorage.getInstance();
		validTestStorageFolder.mkdir();
		testSettings.changeFilePath(validStorageFilePath);
		testSettings = testStorage.loadSettings();
		testMemoriCalendar = testStorage.loadCalendar();
		testStorage.saveCalendar(testMemoriCalendar);
		
		assertEquals(true, validStorageTestPath.exists());
		
	  //next, test by changing file path to invalid file path (target directory does not exist)
		validStorageTestPath.delete();
		testSettings.changeFilePath(invalidStorageFilePath);
		testSettings = testStorage.loadSettings();
		testMemoriCalendar = testStorage.loadCalendar();
		testStorage.saveCalendar(testMemoriCalendar);

		assertEquals(false, defaultStorageTestFile.exists());
		
		renameStorageFile();
	}
	
	/**
	 * Creates a copy of the storage file, memori.json
	 *
	 * @return     true if file is successfully copied
	 */
	private boolean duplicateStoragefile() {	    	
	  InputStream inStream = null;
		OutputStream outStream = null;
		try {  		
			File originalFile =new File(defaultStorageFileName);
	    File copyOfFile =new File(duplicatedDefaultStorageFileName);
	    		
	    inStream = new FileInputStream(originalFile);
	    outStream = new FileOutputStream(copyOfFile);
	        	
	    byte[] buffer = new byte[1024];
	    		
	    int length;
	    //copy the file content in bytes 
	    while ((length = inStream.read(buffer)) > 0){ 
	    	 outStream.write(buffer, 0, length);
	    }
	    
	    inStream.close();
	    outStream.close();

	    return true;
		} catch (IOException e){
	    e.printStackTrace();
	    return false;
		}
	}
	
	/**
	 * Renames the duplicated storage file as "memori.json"
	 *
	 * @return     true if file is successfully renamed
	 */
	private boolean renameStorageFile() {
		File originalFile =new File(duplicatedDefaultStorageFileName);
		File renamedFile =new File(defaultStorageFileName);
		
		if(originalFile.renameTo(renamedFile)){
			return true;
		}else{
			return false;
		}    	
	}
}