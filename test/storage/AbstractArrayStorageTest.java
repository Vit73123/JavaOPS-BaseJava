package storage;

import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    protected static final int STORAGE_LIMIT = 10000;

    private static final int INITIAL_COUNT = 3;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        assertEquals(INITIAL_COUNT, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume r = new Resume(UUID_2);
        storage.update(r);
        Assertions.assertSame(r, storage.get(UUID_2));
    }

    @Test
    public void save() {
        Resume r = new Resume(UUID_4);
        storage.save(r);
        Assertions.assertEquals(INITIAL_COUNT + 1, storage.size());
        Assertions.assertSame(r, storage.get(UUID_4));
    }

    @Test
    public void delete() {
        storage.delete(UUID_2);
        Assertions.assertEquals(INITIAL_COUNT - 1, storage.size());
        Assertions.assertThrows(NotExistStorageException.class, () ->
                storage.get(UUID_2));
    }

    @Test
    public void getAll() {
        Resume[] testStorage = storage.getAll();
        for(Resume r : testStorage) {
            Assertions.assertSame(r, storage.get(r.getUuid()));
        }
        Assertions.assertEquals(testStorage.length, storage.size());
    }

    @Test
    public void get() {
        Assertions.assertEquals(new Resume(UUID_2), storage.get(UUID_2));
    }

    @Test
    public void getNotExist() {
        Exception e = assertThrows(NotExistStorageException.class, () ->
                storage.get("dummy"));
        System.out.println(e.getMessage());
    }

    @Test
    public void overflowStorage() {
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