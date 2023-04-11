package basejava.storage;

import basejava.exception.ExistStorageException;
import basejava.exception.NotExistStorageException;
import basejava.exception.StorageException;
import basejava.model.Resume;
import basejava.util.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.doQuery("DELETE FROM resume", PreparedStatement::executeUpdate);
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.doQuery("SELECT * FROM resume r WHERE r.uuid = ?",
                (ps) -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return new Resume(uuid, rs.getString("full_name"));
                });
    }

    @Override
    public void update(Resume r) {
        if (sqlHelper.doQuery("UPDATE resume SET full_name = ? WHERE uuid = ?",
                (ps) -> {
                    ps.setString(1, r.getFullName());
                    ps.setString(2, r.getUuid());
                    return ps.executeUpdate();
                }) == 0) {
            throw new NotExistStorageException(r.getUuid());
        };
    }

    @Override
    public void save(Resume r) {
        sqlHelper.doQuery("INSERT INTO resume (uuid, full_name) VALUES (?, ?)",
                (ps) -> {
                    ps.setString(1, r.getUuid());
                    ps.setString(2, r.getFullName());
                    try {
                        return ps.executeUpdate();
                    } catch (SQLException e) {
                        if (e.getSQLState().equals("23505")) {
                            throw new ExistStorageException(r.getUuid());
                        } else {
                            throw new StorageException(e);
                        }
                    }
                });
    }

    @Override
    public void delete(String uuid) {
        if (sqlHelper.doQuery("DELETE FROM resume WHERE uuid = ?",
                (ps) -> {
                    ps.setString(1, uuid);
                    return ps.executeUpdate();
                }) == 0) {
            throw new NotExistStorageException(uuid);
        };
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        return sqlHelper.doQuery("SELECT * FROM resume r ORDER BY full_name",
                (ps) -> {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
                    }
                    return resumes;
                });
    }

    @Override
    public int size() {
        return sqlHelper.doQuery("SELECT COUNT(*) FROM resume",
                ps -> {
                    ps.executeQuery();
                    ResultSet rs = ps.getResultSet();
                    rs.next();
                    return rs.getInt(1);
                });
    }
}