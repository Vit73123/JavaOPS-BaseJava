package storage.stream;

import model.Resume;
import storage.AbstractPathStorage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PathStorage extends AbstractPathStorage {

    Strategy serializer;

    PathStorage(Strategy serializer, String dir) {
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
