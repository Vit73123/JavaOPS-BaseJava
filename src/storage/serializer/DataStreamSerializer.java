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
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();

            writeWithException(contacts.entrySet(), dos, contact -> {
                dos.writeUTF(contact.getKey().name());
                dos.writeUTF(contact.getValue());
            });

            // TODO implements sections
            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());

            writeWithException(sections.entrySet(), dos, section -> {
                SectionType sectionType = section.getKey();
                dos.writeUTF(sectionType.name());
//                System.out.println(entry.getValue().getClass().getSimpleName());
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> {
                        dos.writeUTF(getStringWrite(((TextSection) section.getValue()).getContent()));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> organizations = ((OrganizationSection) section.getValue()).getOrganizations();
                        writeWithException(organizations, dos, organization -> {
                            dos.writeUTF(getStringWrite((organization.getHomePage().getName())));
                            dos.writeUTF(getStringWrite((organization.getHomePage().getUrl())));
                            List<Organization.Position> positions = organization.getPositions();
                            writeWithException(positions, dos, position -> {
                                dos.writeUTF(position.getStartDate().toString());
                                dos.writeUTF(position.getEndDate().toString());
                                dos.writeUTF(getStringWrite(position.getTitle()));
                                dos.writeUTF(getStringWrite(position.getDescription()));
                            });
                        });
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> list = ((ListSection) section.getValue()).getItems();
                        writeWithException(list, dos, item -> {
                            dos.writeUTF(item);
                        });
                    }
                }
            });
        }
    }

    private <T> void writeWithException (Collection<T> collection, DataOutputStream dos, WriteConsumer<T> writeConsumer)
            throws IOException {
        dos.writeInt(collection.size());
        for(T t: collection) {
            writeConsumer.write(t);
        }
    }

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
            readWithException(resume, dis, resumeItem -> {
                    ((Resume) resumeItem).addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
                });
            // TODO implements sections
            readWithException(resume, dis, resumeItem -> {
                SectionType sectionType = valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> {
                        ((Resume) resumeItem).addSection(sectionType, new TextSection(getStringRead(dis.readUTF())));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> organizations = new ArrayList<>();
                        readWithException(organizations, dis, organizationItems -> {
                            Link homePage = new Link(getStringRead(dis.readUTF()), getStringRead(dis.readUTF()));
                            List<Organization.Position> positions = new ArrayList<>();
                            readWithException(positions, dis, positionItems -> {
                                    ((List<Organization.Position>) positionItems).add(new Organization.Position(
                                            LocalDate.parse(dis.readUTF(), dateTimeFormatter),
                                            LocalDate.parse(dis.readUTF(), dateTimeFormatter),
                                            getStringRead(dis.readUTF()),
                                            getStringRead(dis.readUTF())));
                            });
                            ((List<Organization>) organizationItems).add(new Organization(homePage, positions));
                        });
                        ((Resume) resumeItem).addSection(sectionType, new OrganizationSection(organizations));
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> list = new ArrayList<>();
                        readWithException(list, dis, listItems -> {
                            ((List<String>) list).add(dis.readUTF());
                        });
                        ((Resume) resumeItem).addSection(sectionType, new ListSection(list));
                    }
                }
            });
            return resume;
        }
    }

    private void readWithException (Object item, DataInputStream dis, ReadConsumer readConsumer)
            throws IOException {
        int size = dis.readInt();
        for(int i = 0; i < size; i++) {
            readConsumer.read(item);
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
