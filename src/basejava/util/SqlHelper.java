package basejava.util;

import basejava.exception.StorageException;
import basejava.sql.ConnectionFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlHelper {

    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public ResultSet select(String stmt, String ... params) throws SQLException {
        ResultSet rs = selectQuery(stmt, params);
        return rs;
    }

    public int insert(String stmt, String ... params) {
        try {
            return updateQuery(stmt, params);
        } catch (SQLException e) {
            return 0;
        }
    }

    public int delete(String stmt, String ... params) {
        try {
            return updateQuery(stmt, params);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public int update(String stmt, String ... params) {
        try {
            return updateQuery(stmt, params);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    private int updateQuery(String stmt, String[] params) throws SQLException {
        try (PreparedStatement ps = getPreparedStatement(stmt, params)) {
            return ps.executeUpdate();
        }
    }

    public ResultSet selectQuery(String stmt, String[] params) throws SQLException {
        PreparedStatement ps = getPreparedStatement(stmt, params);
        return ps.executeQuery();
    }

    private PreparedStatement getPreparedStatement(String stmt, String ... params) throws SQLException {
        PreparedStatement ps = connectionFactory.getConnection().prepareStatement(stmt);
        for(int i = 0; i < params.length; i++) {
            ps.setString(i + 1, params[i]);
        }
        return ps;
    }
}
