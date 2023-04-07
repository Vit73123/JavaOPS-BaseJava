package basejava.storage;

import basejava.exception.ExistStorageException;
import basejava.exception.NotExistStorageException;
import basejava.exception.StorageException;
import basejava.model.Resume;
import basejava.sql.ConnectionFactory;
import basejava.util.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    public final ConnectionFactory connectionFactory;

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        sqlHelper.delete("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        try (ResultSet rs = sqlHelper.select("SELECT * FROM resume r WHERE r.uuid = ?", uuid)) {
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void update(Resume r) {
        if (sqlHelper.update("UPDATE resume SET full_name = ? WHERE uuid = ?",
                r.getFullName(),
                r.getUuid()) == 0) {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    @Override
    public void save(Resume r) {
        if (sqlHelper.insert("INSERT INTO resume (uuid, full_name) VALUES (?, ?)",
                r.getUuid(),
                r.getFullName()) == 0) {
            throw new ExistStorageException(r.getUuid());
        };
    }

    @Override
    public void delete(String uuid) {
        if (sqlHelper.delete("DELETE FROM resume WHERE uuid = ?", uuid) == 0) {
            throw new NotExistStorageException(uuid);
        };
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        try (ResultSet rs = sqlHelper.select("SELECT * FROM resume r ORDER BY uuid")) {
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name").trim()));
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return resumes;
    }

    @Override
    public int size() {
        try (ResultSet rs = sqlHelper.select("SELECT COUNT(*) FROM resume")) {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}