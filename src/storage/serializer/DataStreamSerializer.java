package storage.serializer;

import model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static model.SectionType.*;

public class DataStreamSerializer implements StreamSerializer {

    DateTimeFormatter dateTimeFormatter;

    public DataStreamSerializer() {
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try(DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for(Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            // TODO implements sections
            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for(Map.Entry<SectionType, Section> section : sections.entrySet()) {
                dos.writeUTF(section.getKey().name());
//                System.out.println(entry.getValue().getClass().getSimpleName());
                switch (section.getValue().getClass().getSimpleName()) {
                    case "TextSection":
                        dos.writeUTF("TextSection");
                        dos.writeUTF(((TextSection) section.getValue()).getContent());
                        break;
                    case "OrganizationSection":
                        dos.writeUTF("OrganizationSection");
                        List<Organization> organizations = ((OrganizationSection) section.getValue()).getOrganizations();
                        dos.writeInt(organizations.size());
                        for(Organization organization : organizations) {
                            dos.writeUTF((organization.getHomePage().getName()));
                            dos.writeUTF((organization.getHomePage().getUrl()));
                            List<Organization.Position> positions = organization.getPositions();
                            dos.writeInt(positions.size());
                            for(Organization.Position position : organization.getPositions()) {
                                dos.writeUTF(position.getStartDate().toString());
                                dos.writeUTF(position.getEndDate().toString());
                                dos.writeUTF(position.getTitle());
                                dos.writeUTF(position.getDescription());
                            }
                        }
                        break;
                    case "ListSection":
                        dos.writeUTF("ListSection");
                        List<String> list = ((ListSection) section.getValue()).getItems();
                        dos.writeInt(list.size());
                        for(String item : list) {
                            dos.writeUTF(item);
                        }
                        break;
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            // TODO implements sections
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType sectionType = valueOf(dis.readUTF());
                String section = dis.readUTF();
                switch (section) {
                    case "TextSection":
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case "OrganizationSection":
                        int orgSize = dis.readInt();
                        List<Organization> organizations = new ArrayList<>();
                        List<Organization.Position> positions = new ArrayList<>();
                        for (int j = 0; j < orgSize; j++) {
                            Link homePage = new Link(dis.readUTF(), dis.readUTF());
                            int posSize = dis.readInt();
                            for(int k = 0; k < posSize; k++) {
                                positions.add(new Organization.Position(
                                        LocalDate.parse(dis.readUTF(), dateTimeFormatter),
                                        LocalDate.parse(dis.readUTF(), dateTimeFormatter),
                                        dis.readUTF(),
                                        dis.readUTF()));
                            }
                            organizations.add(new Organization(homePage, positions));
                        }
                        resume.addSection(sectionType, new OrganizationSection(organizations));
                        break;
                    case "ListSection":
                        int listSize = dis.readInt();
                        List<String> list = new ArrayList<>();
                        for (int j = 0; j < listSize; j++) {
                            list.add(dis.readUTF());
                        }
                        resume.addSection(sectionType, new ListSection(list));
                        break;
                }
            }
            return resume;
        }
    }
}
