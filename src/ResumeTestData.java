import model.*;

import java.time.LocalDate;
import java.util.Arrays;

public class ResumeTestData {

    public Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.getContacts().put(ContactType.PHONE, "+0(000) 000-0000");
        resume.getContacts().put(ContactType.MOBILE, "+0(000) 000-0000");
        resume.getContacts().put(ContactType.HOME_PHONE, "+0(000) 000-0000");
        resume.getContacts().put(ContactType.SKYPE, "skype");
        resume.getContacts().put(ContactType.MAIL, "mail");
        resume.getContacts().put(ContactType.LINKEDIN, "LinkedIn");
        resume.getContacts().put(ContactType.GITHUB, "GitHub");
        resume.getContacts().put(ContactType.STACKOVERFLOW, "Stackoverflow");
        resume.getContacts().put(ContactType.HOME_PAGE, "Home page");

        resume.getSections().put(SectionType.OBJECTIVE, new TextSection("""
                Моя позиция."""));
        resume.getSections().put(SectionType.PERSONAL, new TextSection("""
                Мои личные качества."""));
        resume.getSections().put(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("""
                        Моё достижение 1""",
                """
                        Моё достижение 2.""",
                """
                        Моё достижение 3.""")));
        resume.getSections().put(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("""
                        Моя квалификация 1""",
                """
                        Моя квалификация 2""",
                """                       
                        Моя квалификация 3""")));
        resume.getSections().put(SectionType.EXPERIENCE, new OrganizationSection(Arrays.asList(
                new Organization(
                        "Моя организация 3",
                        "Странца организации 3",
                        Arrays.asList(
                                new Period(
                                        "Должность 2",
                                        "Обязанности должности 2",
                                        LocalDate.of(2021, 10, 1),
                                        LocalDate.now()),
                                new Period(
                                        "Должность 1",
                                        "Обязанности должности 1",
                                        LocalDate.of(2020, 3, 1),
                                        LocalDate.of(2021, 10, 1)))),
                new Organization(
                        "Моя организация 2",
                        "Странца организации 2",
                        Arrays.asList(
                                new Period(
                                        "Должность 2",
                                        "Обязанности должности 2",
                                        LocalDate.of(2019, 5, 1),
                                        LocalDate.of(2020, 3, 1)),
                                new Period(
                                        "Должность 1",
                                        "Обязанности должности 1",
                                        LocalDate.of(2018, 8, 1),
                                        LocalDate.of(2019, 5, 1)))),
                new Organization(
                        "Моя организация 1",
                        "Странца организации 1",
                        Arrays.asList(
                                new Period(
                                        "Должность 2",
                                        "Обязанности должности 2",
                                        LocalDate.of(2017, 9, 1),
                                        LocalDate.of(2018, 8, 1)),
                                new Period(
                                        "Должность 1",
                                        "Обязанности должности 1",
                                        LocalDate.of(2016, 7, 1),
                                        LocalDate.of(2017, 9, 1)))))));
        resume.getSections().put(SectionType.EDUCATION, new OrganizationSection(Arrays.asList(
                new Organization(
                        "Мой курс 1",
                        "Странца курса 1",
                        Arrays.asList(
                                new Period(
                                        "Курс 1",
                                        "",
                                        LocalDate.of(2016, 9, 1),
                                        LocalDate.of(2017, 5, 1)))),
                new Organization(
                        "Моё учреждение 1",
                        "Странца учреждения 1",
                        Arrays.asList(
                                new Period(
                                        "Аспирантура",
                                        "",
                                        LocalDate.of(2015, 9, 1),
                                        LocalDate.of(2016, 7, 1)),
                                new Period(
                                        "Специалитет",
                                        "",
                                        LocalDate.of(2013, 9, 1),
                                        LocalDate.of(2015, 9, 1)),
                                new Period(
                                        "Бакалавриат",
                                        "",
                                        LocalDate.of(2012, 9, 1),
                                        LocalDate.of(2015, 9, 1)))))));
        return resume;
    }
}