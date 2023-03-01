package storage;

import storage.serializer.DataStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest {

    protected DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamSerializer()));
    }
}