package basejava.storage;

import basejava.exception.NotExistStorageException;
import basejava.model.ContactType;
import basejava.model.Resume;
import basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("""
            DELETE
              FROM resume""");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Resume resume;
            try (PreparedStatement ps = conn.prepareStatement("""
                    SELECT      *
                      FROM      resume r 
                      WHERE     r.uuid = ?""")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
            }
            try (PreparedStatement ps = conn.prepareStatement("""
                    SELECT      *
                      FROM      contact c
                      WHERE     c.resume_uuid = ?""")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String value = rs.getString("value");
                    ContactType type = ContactType.valueOf(rs.getString("type"));
                    resume.addContact(type, value);
                }
            }
            return resume;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("""
                    update  resume
                      set   full_name = ?
                      where uuid = ?""")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("""
                    update  contact
                      set   type = ?,
                            value = ?
                      where uuid = ?""")) {
                for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                    ps.setString(1, e.getKey().name());
                    ps.setString(2, e.getValue());
                    ps.setString(3, r.getUuid());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("""
                    INSERT
                      INTO   resume (uuid, full_name)
                      VALUES (?, ?)""")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            try (PreparedStatement ps = conn.prepareStatement("""
                    INSERT
                      INTO   contact (resume_uuid, type, value)
                      VALUES (?, ?, ?)""")) {
                for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                    ps.setString(1, r.getUuid());
                    ps.setString(2, e.getKey().name());
                    ps.setString(3, e.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.<Void>execute("""
            delete
              from  resume
              where uuid = ?""",
            ps -> {
                ps.setString(1, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
                return null;
            });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
            List<Resume> resumes = new ArrayList<>();
            try (PreparedStatement ps = conn.prepareStatement("""
                    SELECT      *
                      FROM      resume r
                      order by  full_name, uuid""")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                    try (PreparedStatement subps = conn.prepareStatement("""
                            SELECT      *
                              FROM      contact c
                              WHERE     c.resume_uuid = ?""")) {
                        subps.setString(1, resume.getUuid());
                        ResultSet subrs = subps.executeQuery();
                        while (subrs.next()) {
                            ContactType type = ContactType.valueOf(subrs.getString("type"));
                            String value = subrs.getString("value");
                            resume.addContact(type, value);
                        }
                    }
                    resumes.add(resume);
                }
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("""
            select count(*)
              from resume""",
            ps -> {
                ResultSet rs = ps.executeQuery();
                return rs.next() ? rs.getInt(1) : 0;
            });
    }
}