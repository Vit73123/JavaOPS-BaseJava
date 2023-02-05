package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {

    private String name;
    private Link homePage;
    private List<Period> periods;

    public Organization(String name, String url, List<Period> periods) {
        this.name = name;
        this.homePage = new Link(name, url);
        this.periods = periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(name, that.name) && Objects.equals(homePage, that.homePage) && Objects.equals(periods, that.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, homePage, periods);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "name='" + name + '\'' +
                ", homePage=" + homePage +
                ", periods=" + periods +
                '}';
    }
}
