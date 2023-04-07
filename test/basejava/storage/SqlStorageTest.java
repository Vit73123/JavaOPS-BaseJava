package basejava.storage;

class SqlStorageTest extends AbstractStorageTest {

    protected SqlStorageTest() {
        super(new SqlStorage(DB_URL, DB_USER, DB_PASSWORD));
    }
}