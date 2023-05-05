package basejava.storage;

import basejava.Config;
import basejava.exception.ExistStorageException;
import basejava.exception.NotExistStorageException;
import basejava.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static basejava.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    protected final Storage storage;

    private static final int INITIAL_SIZE = 3;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    public void size() {
        assertSize(INITIAL_SIZE);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        assertEquals(Collections.emptyList(), storage.getAllSorted());
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1, "New Name");

        R1.setContact(ContactType.MAIL, "mail@google.com");
        R1.setContact(ContactType.SKYPE, "NewSkype");
        R1.setContact(ContactType.MOBILE, "+7 921 222-22-22");

        storage.update(newResume);
//        assertSame(newResume, storage.get(UUID_1));
        assertTrue(newResume.equals(storage.get(UUID_1)));
    }

    @Test
    public void updateNotExist() {
        assertThrows(NotExistStorageException.class,() ->
                storage.update(R4));
    }

    @Test
    public void save() {
        storage.save(R4);
        assertGet(R4);
        assertSize(INITIAL_SIZE + 1);
    }

    @Test
    public void saveExist() {
        assertThrows(ExistStorageException.class,() ->
                storage.save(R2));
    }

    @Test
    public void delete() {
        storage.delete(UUID_2);
        assertSize(INITIAL_SIZE - 1);
    }

    @Test
    public void deleteNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () ->
                storage.delete(UUID_4));
    }

    @Test
    public void getAllSorted() {
        List<Resume> list =  storage.getAllSorted();
        assertEquals(3, list.size());
        List<Resume> sortedResumes = Arrays.asList(R1, R2, R3);
        Collections.sort(sortedResumes);
        Assertions.assertEquals(sortedResumes, list);
    }

    @Test
    public void get() {
        assertGet(R1);
        assertGet(R2);
        assertGet(R3);
    }

    private void assertGet(Resume resume) {
        Assertions.assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test
    public void getNotExist() {
        Exception e = assertThrows(NotExistStorageException.class, () ->
                storage.get(UUID_4));
        System.out.println(e.getMessage());
    }
}