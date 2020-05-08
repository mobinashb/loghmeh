package ie.projects.phase7.repository;

import ie.projects.phase7.configs.ConnectionPoolConfig;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static ConnectionPool instance = new ConnectionPool();

    public static ConnectionPool getInstance() {
        return instance;
    }

    private static BasicDataSource dataSource = new BasicDataSource();

    static {
        dataSource.setDriverClassName(ConnectionPoolConfig.DRIVER_CLASS_NAME);
        dataSource.setUrl(ConnectionPoolConfig.DATABASE_URL);
        dataSource.setUsername(ConnectionPoolConfig.DATABASE_USERNAME);
        dataSource.setPassword(ConnectionPoolConfig.DATABASE_PASSWORD);
        dataSource.setMinIdle(ConnectionPoolConfig.DATABASE_MIN_IDLE);
        dataSource.setMaxIdle(ConnectionPoolConfig.DATABASE_MAX_IDLE);
        dataSource.setMaxOpenPreparedStatements(ConnectionPoolConfig.DATABASE_OPEN_PREPARED_STATEMENTS);
    }

    public static Connection getConnection() throws SQLException{
        return dataSource.getConnection();
    }
}