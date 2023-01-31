package model;

public enum ContactType {
    TYPE_1("type 1");

    private final String title;

    ContactType(String contactType) {
        this.title = contactType;

    }

    public String getTitle() {
        return title;
    }
}
