package storage;

import exception.StorageException;
import model.Resume;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path>{

    private Path directory;

    protected AbstractPathStorage(String  dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("File delete error", null);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected void doUpdate(Resume r, Path path) {
        try {
            doWrite(r, Files.newOutputStream(path));
        } catch (IOException e) {
            throw new StorageException("File write error", path.toString(), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file ", path.toString(), e);
        }
        doUpdate(r, path);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return doRead(Files.newInputStream(path));
        } catch (IOException e) {
            throw new StorageException("File read error ", path.toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", path.toString());
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        DirectoryStream<Path> files;
        try {
            files = Files.newDirectoryStream(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
        List<Resume> list = new ArrayList<>(size());
        for(Path file : files) {
            list.add(doGet(file));
        }
        return list;
    }

    protected abstract void doWrite(Resume r, OutputStream Path) throws IOException;

    protected abstract Resume doRead(InputStream Path) throws IOException;
}
