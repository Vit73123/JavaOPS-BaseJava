package basejava.util;

import basejava.exception.StorageException;
import basejava.sql.ConnectionFactory;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <T> T doQuery(String stmt, Processor<T> processor) {
        try (PreparedStatement ps = connectionFactory.getConnection().prepareStatement(stmt)) {
/*
            for (int i = 0; i < params.length; i++) {
                ps.setString(i + 1, params[i]);
            }
*/
            return processor.process(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public interface Processor<T> {

        T process(PreparedStatement ps) throws SQLException;
    }
}
