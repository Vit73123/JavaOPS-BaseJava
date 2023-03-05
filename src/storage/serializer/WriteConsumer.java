package storage.serializer;

import java.util.Collection;

public interface WriteConsumer<T extends Collection> {

    void write(T item);
}
