package basejava.storage;

import basejava.Config;

class SqlStorageTest extends AbstractStorageTest {

    protected SqlStorageTest() {
        super(Config.get().getStorage());
    }
}