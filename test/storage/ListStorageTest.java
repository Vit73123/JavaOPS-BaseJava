package storage;

import model.Resume;

public class ListStorageTest extends AbstractStorageTest {

    public ListStorageTest() {
        super(new ListStorage());
    }

    protected Resume[] getAllExpected(Resume[] expected) {
        return expected;
    }
}