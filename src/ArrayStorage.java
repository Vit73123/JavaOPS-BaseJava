import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    void clear() {
        for (int i = 0; i < size; i++)
            storage[i] = null;
        size = 0;
    }

    void save(Resume r) {
        if (size < storage.length)
            storage[size++] = r;
    }

    Resume get(String uuid) {
        for (Resume r : storage)
            if (r.uuid.equals(uuid)) return r;
        return null;
    }

    void delete(String uuid) {
        boolean isDeleted = false;
        for (int i = 0; i < size; i++)
            if (storage[i].uuid.equals(uuid))
                isDeleted = true;
            else if (isDeleted)
                storage[i - 1] = storage[i];
        if (isDeleted) storage[size--] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }
}
