package storage;

import exception.StorageException;
import model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage) ;
    }

    @Test
    public void saveOverflow() {
        try {
            while (storage.size() < STORAGE_LIMIT) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assertions.fail("Failed: Too early overflow");
        }
        Assertions.assertThrows(StorageException.class, () ->
                storage.save(new Resume()));
    }
}