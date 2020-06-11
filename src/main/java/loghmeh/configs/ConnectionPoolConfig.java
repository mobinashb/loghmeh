package loghmeh.configs;

public class ConnectionPoolConfig {
    public static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    public static final String DATABASE_URL = "jdbc:mysql://loghmeh-mysql:3306/Loghmeh";
//    public static final String DATABASE_URL = "jdbc:mysql://loghmeh-mysql:3306/Loghmeh?useUnicode=true&characterEncoding=UTF-8";
//    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/Loghmeh?useUnicode=true&characterEncoding=UTF-8";
    public static final String DATABASE_USERNAME = "root";
    public static final String DATABASE_PASSWORD = "2213";
//    public static final String DATABASE_PASSWORD = "omidomid";
    public static final int DATABASE_MIN_IDLE = 5;
    public static final int DATABASE_MAX_IDLE = 10;
    public static final int DATABASE_OPEN_PREPARED_STATEMENTS = 100;
}
