package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private final List<String> list = new ArrayList<>();

    public List<String> getList() {
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayList list = (ArrayList) o;
        return Arrays.equals(this.list.toArray(), list.toArray());
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }
}
