package basejava.model;

import java.util.Objects;

public class TextSection extends Section {

    private static final long serialVersionUID = 1L;

    public static final TextSection EMPTY = new TextSection("");

    private String content;

    public TextSection() {
    }

    public TextSection(String content) {
        Objects.requireNonNull(content, "content must nut be null");
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return content != null ? Objects.hash(content) : 0;
    }

    @Override
    public String toString() {
        return content;
    }
}
