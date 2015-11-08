package memori;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import memori.Storage.SettingsTest;
import memori.Storage.StorageTest;
import memori.googleSync.GoogleCRUDTest;
import memori.googleSync.MemoriSyncTest;
import memori.parsers.parserTesting.AddParserTest;
import memori.parsers.parserTesting.IndexesParserTest;
import memori.parsers.parserTesting.MemoriParserTest;
import memori.parsers.parserTesting.SearchParserTest;
import memori.parsers.parserTesting.SortParserTest;
import memori.parsers.parserTesting.UpdateParserTest;
@RunWith(Suite.class)
@Suite.SuiteClasses({
   MemoriTest.class,
   GoogleCRUDTest.class,
   MemoriSyncTest.class,
   AddParserTest.class,
   IndexesParserTest.class,
   MemoriParserTest.class,
   SearchParserTest.class,
   SortParserTest.class,
   UpdateParserTest.class,
   StorageTest.class,
   SettingsTest.class,
   
})
public class TestSuite {   
}  