//@@author A0121262X
package memori.Storage;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import memori.logic.MemoriCalendar;

public class SettingsTest {
	String defaultSettingsFileName = "settings.json";
	String defaultStorageFileName = "memori.json";
	String ValidStorageFolderName = "storageLocation";
	String validStorageFilePath = "storageLocation/memori.json";
	String invalidStorageFilePath = "wrongStorageLocation/memori.json";
	File defaultSettingsTestFile = new File(defaultSettingsFileName);
	File defaultStorageTestFile = new File(defaultStorageFileName);
	File validTestStorageFolder = new File(ValidStorageFolderName);
	File validStorageTestPath = new File(ValidStorageFolderName + "/" + defaultStorageFileName);
	private MemoriCalendar testMemoriCalendar;

	@Test
	public void testWriteSettingsFile() {
		defaultSettingsTestFile.delete();
		
		assertEquals(false,defaultSettingsTestFile.exists());

		MemoriSettings testSettings = MemoriSettings.getInstance();
		testSettings.writeSettingsFile();
		
		assertEquals(true, defaultSettingsTestFile.exists());
	}

	@Test
	public void testChangeStorageFileLocation() {
		defaultStorageTestFile.delete();
		
		MemoriSettings testSettings = MemoriSettings.getInstance();
		MemoriStorage testStorage = MemoriStorage.getInstance();
		validTestStorageFolder.mkdir();
		testSettings.changeFilePath(validStorageFilePath);
		testSettings = testStorage.loadSettings();
		testMemoriCalendar = testStorage.loadCalendar();
		testStorage.saveCalendar(testMemoriCalendar);
		
		assertEquals(true, validStorageTestPath.exists());
		
		validStorageTestPath.delete();
		testSettings.changeFilePath(invalidStorageFilePath);
		testSettings = testStorage.loadSettings();
		testMemoriCalendar = testStorage.loadCalendar();
		testStorage.saveCalendar(testMemoriCalendar);

		assertEquals(false, defaultStorageTestFile.exists());
	}
}
