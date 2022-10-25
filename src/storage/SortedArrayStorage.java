package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int saveResume(Resume r) {
        int index = findSearchKey(r.getUuid());
        if (index >= 0) {
            return -1;
        } else {
            index = Math.abs(index);
        }
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = r;
        return index;
    }

    @Override
    public void deleteResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index);
    }

    @Override
    protected int findSearchKey(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
