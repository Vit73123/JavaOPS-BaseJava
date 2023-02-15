package storage;

public class ObjectStreamPathStorageTestOld extends AbstractStorageTest {

    protected ObjectStreamPathStorageTestOld() {
        super(new ObjectStreamPathStorage(STORAGE_DIR.toPath()));
    }
}