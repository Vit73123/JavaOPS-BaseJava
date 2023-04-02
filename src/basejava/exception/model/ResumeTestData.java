package basejava.exception.model;

import java.time.Month;
import java.util.Arrays;

public class ResumeTestData {

    public static void main(String[] args) {
        ResumeTestData rtd = new ResumeTestData();
        rtd.createResume("uuid1", "name1");
    }

    public Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.addContact(ContactType.PHONE, "+0(000) 000-0000");
        resume.addContact(ContactType.MOBILE, "+0(000) 000-0000");
        resume.addContact(ContactType.HOME_PHONE, "+0(000) 000-0000");
        resume.addContact(ContactType.SKYPE, "skype");
        resume.addContact(ContactType.MAIL, "mail");
        resume.addContact(ContactType.LINKEDIN, "LinkedIn");
        resume.addContact(ContactType.GITHUB, "GitHub");
        resume.addContact(ContactType.STACKOVERFLOW, "Stackoverflow");
        resume.addContact(ContactType.HOME_PAGE, "Home page");

        resume.addSection(SectionType.OBJECTIVE, new TextSection("""
                Моя позиция."""));
        resume.addSection(SectionType.PERSONAL, new TextSection("""
                Мои личные качества."""));
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("""
                        Моё достижение 1""",
                """
                        Моё достижение 2.""",
                """
                        Моё достижение 3.""")));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("""
                        Моя квалификация 1""",
                """
                        Моя квалификация 2""",
                """                       
                        Моя квалификация 3""")));
        resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization(
                        "Моя организация 3",
                        "Странца организации 3",
                        new Organization.Position(
                                2021, Month.OCTOBER,
                                "Должность 2", "Обязанности должности 2"),
                        new Organization.Position(
                                2020, Month.MARCH, 2021, Month.OCTOBER,
                                "Должность 1", "Обязанности должности 1")),
                new Organization(
                        "Моя организация 2",
                        "Странца организации 2",
                        new Organization.Position(
                                2019, Month.MAY,2020, Month.MARCH,
                                "Должность 2", "Обязанности должности 2"),
                        new Organization.Position(
                                2018, Month.AUGUST, 2019, Month.MAY,
                                "Должность 1", "Обязанности должности 1")),
                new Organization(
                        "Моя организация 1",
                        "Странца организации 1",
                        new Organization.Position(
                                2017, Month.SEPTEMBER,2018, Month.AUGUST,
                                "Должность 2", "Обязанности должности 2"),
                        new Organization.Position(
                                2016, Month.JULY, 2017, Month.SEPTEMBER,
                                "Должность 1", "Обязанности должности 1"))));
        resume.addSection(SectionType.EDUCATION, new OrganizationSection(
                new Organization(
                        "Мой курс 1",
                        "Странца курса 1",
                        new Organization.Position(
                                2016, Month.SEPTEMBER,2017, Month.MAY,
                                "Курс 1","")),
                new Organization(
                        "Моё учреждение 1",
                        "Странца учреждения 1",
                        new Organization.Position(
                                2015, Month.SEPTEMBER,2016, Month.JULY,
                                "Аспирантура", ""),
                        new Organization.Position(
                                2014, Month.SEPTEMBER,2015, Month.SEPTEMBER,
                                "Специалитет", ""),
                        new Organization.Position(
                                2012, Month.SEPTEMBER,2014, Month.SEPTEMBER,
                                "Бакалавриат", ""))));
        return resume;
    }
}