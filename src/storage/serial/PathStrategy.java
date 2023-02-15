package storage.serial;

import model.Resume;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class PathStrategy {

    protected void doWrite(Resume r, OutputStream os) throws IOException {
        try(ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(r);
        }
    }
}
