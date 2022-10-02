import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (Resume resume : storage) {
            if (resume == null) break;
            resume = null;
        }
    }

    void save(Resume r) {
        int index = size();
        if (index < storage.length)
            storage[index] = r;
    }

    Resume get(String uuid) {
        Resume resume = null;
        for (Resume r : storage)
            if (r.uuid.equals(uuid)) {
                resume = r;
                break;
            }
        return resume;
    }

    void delete(String uuid) {
        boolean isDeleted = false;
        int i = 0;
        while (storage[i] != null) {
            if (storage[i].uuid.equals(uuid))
                isDeleted = true;
            else if (isDeleted)
                storage[i - 1] = storage[i];
            i++;
        }
        if (isDeleted) storage[i - 1] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size());
    }

    int size() {
        int i = 0;
        for (Resume r : storage) {
            if (r == null) break;
            i++;
        }
        return i;
    }
}
