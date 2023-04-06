package basejava.storage;

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
        sqlHelper.doExecute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        ResultSet rs = sqlHelper.doExecuteQuery("SELECT * FROM resume r WHERE r.uuid = ?", uuid);
        try {
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
        sqlHelper.doExecute("UPDATE resume WHERE uuid = ? SET full_name = ?",
                r.getUuid(),
                r.getFullName());
    }

    @Override
    public void save(Resume r) {
        sqlHelper.doExecute("INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                r.getUuid(),
                r.getFullName());
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.doExecute("DELETE FROM resume WHERE uuid = ?", uuid);
    }

    @Override
    public List<Resume> getAllSorted() {
        ResultSet rs = sqlHelper.doExecuteQuery("SELECT * FROM resume r ORDER BY uuid");
        List<Resume> resumes = new ArrayList<>();
        while (true) {
            try {
                while (rs.next()) {
                    resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
                }
            } catch (SQLException e) {
                throw new StorageException(e);
            }
        }
    }

    @Override
    public int size() {
        ResultSet rs = sqlHelper.doExecuteQuery("SELECT * FROM resume r ORDER BY uuid");
        try {
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}