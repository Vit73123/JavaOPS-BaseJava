package storage;


import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import util.JsonParser;

@Suite
@SelectClasses(
        {
                ArrayStorageTest.class,
                SortedArrayStorageTest.class,
                ListStorageTest.class,
                MapResumeStorageTest.class,
                MapUuidStorageTest.class,
                ObjectFileStorageTest.class,
                ObjectPathStorageTest.class,
                XmlPathStorageTest.class,
                JsonPathStorageTest.class
        })

public class AllStorageTest {
}
