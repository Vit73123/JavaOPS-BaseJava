package storage.serializer;

import model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
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

            writeWithException(contacts.entrySet(), dos, () -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

/*
            dos.writeInt(contacts.size());
            for(Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
*/
            // TODO implements sections
            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for(Map.Entry<SectionType, Section> section : sections.entrySet()) {
                SectionType sectionType = section.getKey();
                dos.writeUTF(sectionType.name());
//                System.out.println(entry.getValue().getClass().getSimpleName());
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> {
                            dos.writeUTF(getStringWrite(((TextSection) section.getValue()).getContent()));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> organizations = ((OrganizationSection) section.getValue()).getOrganizations();
                        dos.writeInt(organizations.size());
                        for (Organization organization : organizations) {
                                dos.writeUTF(getStringWrite((organization.getHomePage().getName())));
                                dos.writeUTF(getStringWrite((organization.getHomePage().getUrl())));
                            List<Organization.Position> positions = organization.getPositions();
                            dos.writeInt(positions.size());
                            for (Organization.Position position : organization.getPositions()) {
                                dos.writeUTF(position.getStartDate().toString());
                                dos.writeUTF(position.getEndDate().toString());
                                dos.writeUTF(getStringWrite(position.getTitle()));
                                dos.writeUTF(getStringWrite(position.getDescription()));
                            }
                        }
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> list = ((ListSection) section.getValue()).getItems();
                        dos.writeInt(list.size());
                        for (String item : list) {
                            dos.writeUTF(item);
                        }
                    }
                }
            }
        }
    }

    private <T> void writeWithException (Collection<T> collection, DataOutputStream dos, WriteConsumer<T> writeConsumer)
            throws IOException {
        dos.writeInt(collection.size());
        for(T t: collection) {
            writeConsumer.write(t);
        }
    }

/*
    private void forEach (Map<ContactType, String> contacts, DataOutputStream dos) throws IOException {
        for(Map.Entry<ContactType, String> contact: contacts.entrySet()) {
            dos.writeUTF(contact.getKey().name());
            dos.writeUTF(contact.getValue());
        }
    }
*/

    private String getStringWrite(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
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
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> {
                        resume.addSection(sectionType, new TextSection(getStringRead(dis.readUTF())));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        int orgSize = dis.readInt();
                        List<Organization> organizations = new ArrayList<>();
                        List<Organization.Position> positions = new ArrayList<>();
                        for (int j = 0; j < orgSize; j++) {
                            Link homePage = new Link(getStringRead(dis.readUTF()), getStringRead(dis.readUTF()));
                            int posSize = dis.readInt();
                            for (int k = 0; k < posSize; k++) {
                                positions.add(new Organization.Position(
                                        LocalDate.parse(dis.readUTF(), dateTimeFormatter),
                                        LocalDate.parse(dis.readUTF(), dateTimeFormatter),
                                        getStringRead(dis.readUTF()),
                                        getStringRead(dis.readUTF())));
                            }
                            organizations.add(new Organization(homePage, positions));
                        }
                        resume.addSection(sectionType, new OrganizationSection(organizations));
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        int listSize = dis.readInt();
                        List<String> list = new ArrayList<>();
                        for (int j = 0; j < listSize; j++) {
                            list.add(dis.readUTF());
                        }
                        resume.addSection(sectionType, new ListSection(list));
                    }
                }
            }
            return resume;
        }
    }

    private String getStringRead(String str) {
        if (str.isEmpty()) {
            return null;
        } else {
            return str;
        }
    }
}
