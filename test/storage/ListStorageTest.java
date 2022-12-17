package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.*;

class ListStorageTest {

    private final Storage storage = new ListStorage();

    private static final int INITIAL_SIZE = 3;
    private static final int WRONG_INITIAL_SIZE = 10;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_NEW = "uuid_new";
    private static final String UUID_EXIST = UUID_2;
    private static final String UUID_NOT_EXIST = "dummy";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_NEW;
    private static final Resume RESUME_EXIST;
    private static final Resume RESUME_NOT_EXIST;

    static {
        RESUME_1 = new Resume(UUID_1);
        RESUME_2 = new Resume(UUID_2);
        RESUME_3 = new Resume(UUID_3);
        RESUME_NEW = new Resume(UUID_NEW);
        RESUME_EXIST = new Resume(UUID_EXIST);
        RESUME_NOT_EXIST = new Resume(UUID_NOT_EXIST);
    }

//    MyListStorageTest(Storage storage) {
//        this.storage = storage;
//    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    void size() {
        assertSize(INITIAL_SIZE);
    }

    @Test
    public void wrongSize() {
        assertWrongSize(WRONG_INITIAL_SIZE);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    public void assertWrongSize(int wrongSize) {
        assertThrows(AssertionFailedError.class,() ->
                assertSize(wrongSize));
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
        assertArrayEquals(new Resume[0], storage.getAll());
    }

    @Test
    void update() {
        storage.update(RESUME_EXIST);
        assertSame(RESUME_EXIST, storage.get(UUID_EXIST));
    }

    @Test
    public void updateNotExist() {
        assertThrows(NotExistStorageException.class,() ->
                storage.update(RESUME_NOT_EXIST));
    }

    @Test
    void save() {
        storage.save(RESUME_NEW);
        assertGet(RESUME_NEW);
        assertSize(INITIAL_SIZE + 1);
    }

    @Test
    public void saveExist() {
        assertThrows(ExistStorageException.class,() ->
                storage.save(RESUME_EXIST));
    }

    @Test
    void delete() {
        storage.delete(UUID_EXIST);
        assertSize(INITIAL_SIZE - 1);
        assertThrows(NotExistStorageException.class, () ->
                storage.get(UUID_EXIST));
    }

    @Test
    public void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () ->
                storage.delete(UUID_NOT_EXIST));
    }

    @Test
    void getAll() {
        Resume[] expected = new Resume[]{RESUME_1, RESUME_2, RESUME_3};
        assertArrayEquals(storage.getAll(), expected);
    }

    @Test
    void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test
    public void getNotExist() {
        Exception e = assertThrows(NotExistStorageException.class, () ->
                storage.get(UUID_NOT_EXIST));
        System.out.println(e.getMessage());
    }
}