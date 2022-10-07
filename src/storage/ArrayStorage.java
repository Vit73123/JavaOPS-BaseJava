package storage;

import model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (r.getUuid() == null) {
            System.out.println("Ошибка: пустой uuid резюме (" + r.getUuid() + ").");
        } else if (size == storage.length) {
            System.out.println("Ошибка: достигнут предел количества резюме.");
        } else if (findSearchKey(r.getUuid()) != -1) {
            System.out.println("Ошибка: резюме " + r.getUuid() + " уже есть.");
        } else {
            storage[size++] = r;
        }
    }

    public void update(Resume r) {
        int index = findSearchKey(r.getUuid());
        if (index != -1) {
            storage[index] = r;
        } else {
            System.out.println("Ошибка: резюме " + r.getUuid() + " нет.");
        }
    }

    public Resume get(String uuid) {
        int index = findSearchKey(uuid);
        if (index != -1) {
            return storage[index];
        } else {
            System.out.println("Ошибка: резюме " + uuid + " нет.");
            return null;
        }
    }

    public void delete(String uuid) {
        int index = findSearchKey(uuid);
        if (index != -1) {
            storage[index] = storage[--size];
            storage[size] = null;
        } else {
            System.out.println("Ошибка: резюме " + uuid + " нет.");
        }
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
