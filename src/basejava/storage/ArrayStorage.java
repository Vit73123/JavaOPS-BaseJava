package basejava.storage;

import basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected void fillDeletedElement(int index) {
        storage[index] = storage[size];
    }

    protected void insertElement(Resume r, int index) {
        storage[size] = r;
    }

    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
