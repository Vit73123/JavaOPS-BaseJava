package basejava.storage;

import basejava.storage.serializer.DataStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest {

    protected DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamSerializer()));
    }
}