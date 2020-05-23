package ie.projects.phase7.configs;

public class ConnectionPoolConfig {
    public static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    public static final String DATABASE_URL = "jdbc:mysql://localhost/Loghmeh";
    public static final String DATABASE_USERNAME = "root";
    public static final String DATABASE_PASSWORD = "2213";
    public static final int DATABASE_MIN_IDLE = 5;
    public static final int DATABASE_MAX_IDLE = 10;
    public static final int DATABASE_OPEN_PREPARED_STATEMENTS = 100;
}
