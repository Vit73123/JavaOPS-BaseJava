package basejava.storage;

import basejava.Config;

public class SqlStorageTest extends AbstractStorageTest {

    protected SqlStorageTest() {
        super(Config.get().getStorage());
    }
}