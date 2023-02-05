package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;
import model.ResumeTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {
    protected final Storage storage;

    private static final int INITIAL_SIZE = 3;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        ResumeTestData rtd = new ResumeTestData();
        RESUME_1 = rtd.createResume(UUID_1, "Name1");
        RESUME_2 = rtd.createResume(UUID_2, "Name2");
        RESUME_3 = rtd.createResume(UUID_3, "Name3");
        RESUME_4 = rtd.createResume(UUID_4, "Name4");
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
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
        storage.update(newResume);
//        assertSame(newResume, storage.get(UUID_1));
        assertTrue(newResume == storage.get(UUID_1));
    }

    @Test
    public void updateNotExist() {
        assertThrows(NotExistStorageException.class,() ->
                storage.update(RESUME_4));
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(INITIAL_SIZE + 1);
    }

    @Test
    public void saveExist() {
        assertThrows(ExistStorageException.class,() ->
                storage.save(RESUME_2));
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
        Assertions.assertEquals(list, Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
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