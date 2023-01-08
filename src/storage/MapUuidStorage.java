package storage;

import model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {
    private final Map<String, Resume> map = new HashMap<>();

    @Override
    protected Object getIndex(String uuid) {
        return uuid;
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        map.replace((String) searchKey, r);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return map.containsKey((String) searchKey);
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        map.put((String) searchKey, r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return map.get((String) searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        map.remove((String) searchKey);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Resume[] getAll() {
        return map.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return map.size();
    }
}
