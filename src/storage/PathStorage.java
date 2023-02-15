package storage;

import exception.StorageException;
import model.Resume;
import storage.stream.Strategy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {

    private final Path directory;

    private final Strategy serializer;

    protected PathStorage(String  dir, Strategy serializer) {
        Path path = Paths.get(dir);
        Objects.requireNonNull(path, "directory must not be null");
        if (!Files.isDirectory(path) || !Files.isWritable(path)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.directory = path;
        this.serializer = serializer;
    }

    @Override
    public void clear() {
        getFiles().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getFiles().count();
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
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
            throw new StorageException("File delete error", path.toString());
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        Stream<Path> files = getFiles();
        List<Resume> list = new ArrayList<>(size());
        for(Path file : files.toList()) {
            list.add(doGet(file));
        }
        return list;
    }

    private Stream<Path> getFiles() {
        Stream<Path> files;
        try {
            files = Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
        return files;
    }

    public void doWrite(Resume r, OutputStream os) throws IOException {
        serializer.doWrite(r, os);
    }

    public Resume doRead(InputStream is) throws IOException {
        return serializer.doRead(is);
    }
}
