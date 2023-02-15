package storage;

public class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super(new FileStorage(STORAGE_DIR, SERIALIZER));
    }
}