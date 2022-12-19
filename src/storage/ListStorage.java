package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ListStorage extends AbstractStorage {

    List<Resume> storage = new ArrayList<>();

    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    protected void updateResume(int index, Resume r) {
        storage.set(index, r);
    }

    protected void saveResume(int index, Resume resume) {
        storage.add(resume);
    }

    protected Resume getResume(int index) {
        return storage.get(index);
    }

    protected void deleteResume(int index) {
        storage.remove(index);
    }

    protected Object findSearchKey(String uuid) {
        Iterator<Resume> iterator = storage.iterator();
        while(iterator.hasNext()) {
            Resume r = iterator.next();
            if (Objects.equals(r.getUuid(), uuid)) {
                return storage.indexOf(r);
            }
        }
        return null;
    }

    protected boolean isExist(Object searchKey) {
        return (searchKey != null);
    }
}