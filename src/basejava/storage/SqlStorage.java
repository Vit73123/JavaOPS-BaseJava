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
        sqlHelper.doQuery(PreparedStatement::executeUpdate,
            "DELETE FROM resume"
        );
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.doQuery((ps) -> {
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                       throw new NotExistStorageException(uuid);
                    }
                    return new Resume(uuid, rs.getString("full_name"));
                },
                "SELECT * FROM resume r WHERE r.uuid = ?",
                uuid);
    }

    @Override
    public void update(Resume r) {
        if (sqlHelper.doQuery(PreparedStatement::executeUpdate,
                "UPDATE resume SET full_name = ? WHERE uuid = ?",
                r.getFullName(),
                r.getUuid()) == 0) {
            throw new NotExistStorageException(r.getUuid());
        };
    }

    @Override
    public void save(Resume r) {
        sqlHelper.doQuery((ps) -> {
                    try {
                        return ps.executeUpdate();
                    } catch (SQLException e) {
                        throw new ExistStorageException(r.getUuid());
                    }
                },
                "INSERT INTO resume (uuid, full_name) VALUES (?, ?)",
                r.getUuid(),
                r.getFullName());
    }

    @Override
    public void delete(String uuid) {
        if (sqlHelper.doQuery(PreparedStatement::executeUpdate,
                "DELETE FROM resume WHERE uuid = ?",
                uuid) == 0) {
            throw new NotExistStorageException(uuid);
        };
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        return sqlHelper.doQuery((ps) -> {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
                    }
                    return resumes;
                },
                "SELECT * FROM resume r ORDER BY full_name");
    }

    @Override
    public int size() {
        return sqlHelper.doQuery(ps -> {
                ps.executeQuery();
                ResultSet rs = ps.getResultSet();
                rs.next();
                return rs.getInt(1);
            },
            "SELECT COUNT(*) FROM resume");
    }
}