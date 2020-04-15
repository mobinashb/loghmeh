package ie.projects.phase6.repository.restaurant;

public class mapper {
    public static String createTable(String tableName){
        return String.format(
                "CREATE TABLE  %s " +
                        "(id CHAR(24) NOT NULL PRIMARY KEY, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "logo VARCHAR(255) NOT NULL, " +
                        "locationX DOUBLE NOT NULL, " +
                        "locationY DOUBLE NOT NULL)",
                tableName);
    }
    public static String deleteExistingTable(String tableName){
        return String.format("DROP TABLE IF EXISTS %s", tableName);
    }

    public static String insertToTable(String tableName){
        return String.format("INSERT IGNORE INTO %s " +
                "(id, name, logo, locationX, locationY) " +
                "VALUES(?,?,?,?,?)",
                tableName);
    }
}
