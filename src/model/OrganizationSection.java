package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrganizationSection {
    private final List<Organization> organizations = new ArrayList<>();

    public List<Organization> getOrganizations() {
        return organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection check = (OrganizationSection) o;
        return Objects.equals(organizations, check.organizations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizations);
    }

    @Override
    public String toString() {
        return "OrganizationSection{" +
                "organizations=" + organizations +
                '}';
    }
}
