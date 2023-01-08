package storage;

import model.Resume;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

class MapUuidStorageTest extends AbstractStorageTest {

    public MapUuidStorageTest() {
        super(new MapUuidStorage());
    }

    protected Resume[] getAllExpected(Resume[] expected)
            throws NoSuchFieldException, IllegalAccessException {
        Resume[] mapArray = new Resume[storage.size()];
        Class<? extends Storage> storageClass = storage.getClass();
        Field field = storageClass.getDeclaredField("map");
        field.setAccessible(true);
        Map<String, Resume> testMap = (HashMap) field.get(storage);
        int i = 0;
        for(Map.Entry<String, Resume> entry : testMap.entrySet()) {
            mapArray[i] = entry.getValue();
            i++;
        }
        field.setAccessible(false);

        return mapArray;
    }
}