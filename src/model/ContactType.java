package model;

import java.util.Objects;

public class ContactType {
    private final String contactType;

    public ContactType(String contactType) {
        this.contactType = contactType;
    }

    public String get() {
        return contactType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        String contactType = (String) o;
        return Objects.equals(this.contactType, contactType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactType);
    }
}
