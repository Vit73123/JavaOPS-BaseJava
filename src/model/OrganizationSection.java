package model;

import java.util.ArrayList;
import java.util.List;

public class OrganizationSection extends AbstractSection {
    String name;
    String website;
    List<Period> periods = new ArrayList<>();
}
