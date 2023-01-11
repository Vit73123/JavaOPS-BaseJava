package storage;

import model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MapResumeStorageTest extends AbstractStorageTest {

    public MapResumeStorageTest() {
        super(new MapResumeStorage());
    }

    @Test
    void getResume() {
        assertGetResume(RESUME_1);
        assertGetResume(RESUME_2);
        assertGetResume(RESUME_3);
    }

    @Test
    private void assertGetResume(Resume resume) {
        MapResumeStorage mapResumeStorage = (MapResumeStorage) storage;
        Assertions.assertEquals(resume, mapResumeStorage.getResume(resume.getFullName()));
    }

}