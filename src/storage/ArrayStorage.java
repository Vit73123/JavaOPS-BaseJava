package storage;

import model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage implements Storage {
    private static final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int index = findSearchKey(r.getUuid());
        if (index == -1) {
            System.out.println("Ошибка: резюме " + r.getUuid() + " нет.");
        }
        storage[index] = r;
    }

    public void save(Resume r) {
        if (size == storage.length) {
            System.out.println("Ошибка: достигнут предел количества резюме.");
        } else if (findSearchKey(r.getUuid()) != -1) {
            System.out.println("Ошибка: резюме " + r.getUuid() + " уже есть.");
        } else {
            storage[size++] = r;
        }
    }

    public Resume get(String uuid) {
        int index = findSearchKey(uuid);
        if (index == -1) {
            System.out.println("Ошибка: резюме " + uuid + " нет.");
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = findSearchKey(uuid);
        if (index == -1) {
            System.out.println("Ошибка: резюме " + uuid + " нет.");
        }
        storage[index] = storage[--size];
        storage[size] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int findSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
