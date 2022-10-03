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
        for (int i = 0; i < size; i++)
            if (storage[i].uuid.equals(uuid)) return storage[i];
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++)
            if (storage[i].uuid.equals(uuid)) {
                size--;
                storage[i] = storage[size];
                storage[size] = null;
                break;
            }
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
