package storage.serializer;

import java.io.IOException;

@FunctionalInterface
public interface WriteConsumer<T> {

    void write(T item) throws IOException;
}
