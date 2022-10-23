package storage;

import model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected int saveResume(Resume r) {
        int index = findSearchKey(r.getUuid());
        if (index != -1) {
            return -1;
        }
        storage[size++] = r;
        return index;
    }

    protected void deleteResume(int index) {
        storage[index] = storage[--size];
        storage[size] = null;
    }

    protected int findSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
