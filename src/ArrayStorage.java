import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    void save(Resume r) {
        if (findResume(r.uuid) != null)
            System.out.println("Ошибка: резюме " + r.uuid + " уже есть.");
        else if (size < storage.length)
            storage[size++] = r;
    }

    void update(Resume r) {
        Resume resume = get(r.uuid);
        if (resume != null)
            resume = r;
        else
            System.out.println("Ошибка: резюме " + r.uuid + " нет.");
    }

    Resume get(String uuid) {
        Resume resume = findResume(uuid);
        if (resume != null)
            return resume;
        else {
            System.out.println("Ошибка: резюме " + uuid + " нет.");
            return null;
        }
    }

    void delete(String uuid) {
        Resume resume = findResume(uuid);
        if (resume != null) {
            size--;
            resume = storage[size];
            storage[size] = null;
        } else
            System.out.println("Ошибка: резюме " + uuid + " нет.");
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

    Resume findResume(String uuid) {
        for (int i = 0; i < size; i++)
            if (storage[i].uuid.equals(uuid)) return storage[i];
        return null;
    }
}
