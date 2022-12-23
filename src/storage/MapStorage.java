package storage;

import model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    Map<String, Resume> storage = new HashMap<>();

    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    protected void updateResume(Object searchKey, Resume r) {
        storage.replace((String) searchKey, r);
    }

    protected void saveResume(Object searchKey, Resume resume) {
        storage.put((String) searchKey, resume);
    }

    protected Resume getResume(Object searchKey) {
        return storage.get((String) searchKey);
    }

    protected void deleteResume(Object searchKey) {
        storage.remove((String) searchKey);
    }

    protected Object findSearchKey(String uuid) {
        if (storage.containsKey(uuid)) {
            return uuid;
        } else {
            return null;
        }
    }

    protected boolean isExist(Object searchKey) {
        return (searchKey != null);
    }
}
