package basejava.storage;

import basejava.Config;
import basejava.exception.ExistStorageException;
import basejava.exception.NotExistStorageException;
import basejava.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    protected static final String DB_URL = Config.get().getProperty("db.url");
    protected static final String DB_USER = Config.get().getProperty("db.user");
    protected static final String DB_PASSWORD = Config.get().getProperty("db.password");

    protected final Storage storage;

    private static final int INITIAL_SIZE = 3;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume R1;
    private static final Resume R2;
    private static final Resume R3;
    private static final Resume R4;

    static {
        R1 = new Resume(UUID_1, "Name1");
        R2 = new Resume(UUID_2, "Name2");
        R3 = new Resume(UUID_3, "Name3");
        R4 = new Resume(UUID_4, "Name4");

/*
        R1.addContact(ContactType.MAIL, "mail@ya.ru");
        R1.addContact(ContactType.PHONE, "11111");
        R2.addContact(ContactType.SKYPE, "skype2");
        R2.addContact(ContactType.PHONE, "22222");

        R1.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        R1.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
        R1.addSection(SectionType.ACHIEVEMENT, new ListSection("Achievement11,", "Achievement12", "Achievement13"));
        R1.addSection(SectionType.QUALIFICATIONS, new ListSection("Java", "SQL", "JavaScript"));
        R1.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization11", "http://Organization11.ru",
                                new Organization.Position(2005, Month.JANUARY, "position1", "content1"),
                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))));
        R1.addSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Institute", null,
                                new Organization.Position(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
                        new Organization("Organization12", "http://Organization12.ru")));
        R1.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization2", "http://Organization2.ru",
                                new Organization.Position(2015, Month.JANUARY, "position1", "content1"))));
*/
    }

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
        Assertions.assertEquals(list, Arrays.asList(R1, R2, R3));
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