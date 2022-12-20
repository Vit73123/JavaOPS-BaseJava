package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume r) {
        int index = (int) getExistingSearchKey(r.getUuid());
        updateResume(index, r);
    }

    public void save(Resume r) {
        int index = (int) getNotExistingSearchKey(r.getUuid());
        saveResume(index, r);
    }

    public Resume get(String uuid) {
        int index = (int) getExistingSearchKey(uuid);
        return getResume(index);
    }

    @Override
    public void delete(String uuid) {
        int index = (int) getExistingSearchKey(uuid);
        deleteResume(index);
    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            return searchKey;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        } else {
            return size();
        }
    }

    protected abstract void updateResume(int index, Resume r);

    protected abstract void saveResume(int index, Resume r);

    protected abstract Resume getResume(int index);

    protected abstract void deleteResume(int index);

    protected abstract Object findSearchKey(String uuid);

    protected abstract boolean isExist(Object searchKey);
}
