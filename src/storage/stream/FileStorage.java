package storage.stream;

import model.Resume;
import storage.AbstractFileStorage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileStorage extends AbstractFileStorage {

    Strategy serializer;

    FileStorage(Strategy serializer, File dir) {
        super(dir);
        this.serializer = serializer;
    }

    public void doWrite(Resume r, OutputStream os) throws IOException {
        serializer.doWrite(r, os);
    }

    public Resume doRead(InputStream is) throws IOException {
        return serializer.doRead(is);
    }
}
