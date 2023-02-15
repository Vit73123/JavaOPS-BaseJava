package storage.serial;

import model.Resume;

import java.io.IOException;
import java.io.OutputStream;

public class ObjectSerialStorage {

    Strategy strategy;

    ObjectSerialStorage(Strategy strategy) {
        this.strategy = strategy;
    }

    public void doWrite(Resume r, OutputStream os) throws IOException {
        strategy.doWrite(r, os);
    };
}
