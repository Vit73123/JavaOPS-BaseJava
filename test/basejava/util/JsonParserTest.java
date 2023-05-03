package basejava.util;

import basejava.model.Resume;
import basejava.model.Section;
import basejava.model.TextSection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static basejava.TestData.R1;

class JsonParserTest {

    @Test
    void testResume() {
        String json = JsonParser.write(R1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assertions.assertEquals(R1, resume);
    }

    @Test
    void write() {
        Section section1 = new TextSection("Objective1");
        String json = JsonParser.write(section1, Section.class);
        System.out.println(json);
        Section section2 = JsonParser.read(json, Section.class);
        Assertions.assertEquals(section1, section2);
    }
}