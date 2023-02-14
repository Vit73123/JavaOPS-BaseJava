package storage;

import exception.StorageException;
import model.Resume;

import java.io.*;
import java.nio.file.Path;

public class ObjectStreamPathStorageTest extends AbstractPathStorage {

    protected ObjectStreamPathStorageTest(Path directory) {
        super(directory.toString());
    }

    @Override
    protected void doWrite(Resume r, OutputStream os) throws IOException {
        try(ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(r);
        }
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        try(ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException(null, "Error read resume", e);
        }
    }
}
