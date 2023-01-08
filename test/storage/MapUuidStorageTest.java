package storage;

import model.Resume;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

class MapUuidStorageTest extends AbstractStorageTest {

    public MapUuidStorageTest() {
        super(new MapUuidStorage());
    }

    protected Resume[] getAllExpected(Storage storage, Resume[] expected) throws NoSuchFieldException, IllegalAccessException {
        Resume[] storageArray = new Resume[storage.size()];
        Class<? extends Storage> storageClass = storage.getClass();
        Field field = storageClass.getDeclaredField("storage");
        field.setAccessible(true);
        Map<String, Resume> testStorage = (HashMap) field.get(storage);
        int i = 0;
        for(Map.Entry<String, Resume> entry : testStorage.entrySet()) {
            storageArray[i] = entry.getValue();
            i++;
        }
        field.setAccessible(false);

        return storageArray;
    }
}