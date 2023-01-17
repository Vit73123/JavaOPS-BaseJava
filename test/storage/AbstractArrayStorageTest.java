package storage;

public class AbstractArrayStorageTest extends AbstractStorageTest {

    private final Storage storage;

    AbstractArrayStorageTest(Storage storage) {
        super(storage);
        this.storage = storage;
    }
}
