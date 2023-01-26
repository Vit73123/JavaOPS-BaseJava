package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization extends AbstractSection {
    private String name;
    private String website;
    private final List<Period> periods = new ArrayList<>();

    public Organization() {
    }

    public Organization(String name, String website) {
        this.name = name;
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization org = (Organization) o;
        return Objects.equals(name, org.name) &&
                Objects.equals(website, org.website);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, website);
    }

    @Override
    public String toString() {
        return "OrganizationSection{" +
                "name='" + name + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
