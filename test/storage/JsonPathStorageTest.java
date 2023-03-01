package storage;

import storage.serializer.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {

    protected JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonStreamSerializer()));
    }
}