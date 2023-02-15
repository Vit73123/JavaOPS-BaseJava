package storage.serial;

import model.Resume;

import java.io.IOException;
import java.io.OutputStream;

public interface Strategy {

    void doWrite(Resume r, OutputStream os) throws IOException;
}
