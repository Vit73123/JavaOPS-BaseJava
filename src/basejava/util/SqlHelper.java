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

    public void doExecute(String stmt, String ... params) {
        try (PreparedStatement ps = getPreparedStatement(stmt, params)) {
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public ResultSet doExecuteQuery(String stmt, String ... params) {
        try (PreparedStatement ps = getPreparedStatement(stmt, params)) {
            ResultSet rs = ps.executeQuery();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return null;
    }

    private PreparedStatement getPreparedStatement(String stmt, String[] params) throws SQLException {
        PreparedStatement ps = connectionFactory.getConnection().prepareStatement(stmt);
        for(int i = 1; i <= params.length; i++) {
            ps.setString(i, params[i]);
        }
        return ps;
    }
}
