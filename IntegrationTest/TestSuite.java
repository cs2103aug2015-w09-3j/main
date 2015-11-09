//@@author A0098038W
package memori.IntegrationTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import memori.Storage.UnitTest.SettingsTest;
import memori.Storage.UnitTest.StorageTest;
import memori.googleSync.UnitTest.GoogleCRUDTest;
import memori.googleSync.UnitTest.MemoriSyncTest;
import memori.logic.UnitTest.MemoriCalendarTest;
import memori.parsers.UnitTest.AddParserTest;
import memori.parsers.UnitTest.IndexesParserTest;
import memori.parsers.UnitTest.MemoriParserTest;
import memori.parsers.UnitTest.SearchParserTest;
import memori.parsers.UnitTest.SortParserTest;
import memori.parsers.UnitTest.UpdateParserTest;
@RunWith(Suite.class)
@Suite.SuiteClasses({
   MemoriTest.class,
   MemoriCalendarTest.class,
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