package ie.projects.phase6.repository.food;

public class mapper {
    public static String createTable(String tableName){
        return String.format(
                "CREATE TABLE  %s " +
                        "(restaurantId CHAR(24) NOT NULL, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "description VARCHAR(511) NOT NULL, " +
                        "popularity FLOAT NOT NULL, " +
                        "image VARCHAR(255) NOT NULL, " +
                        "price FLOAT NOT NULL, " +
                        "PRIMARY KEY (restaurantId,name)) ",
                tableName);
    }
    public static String deleteExistingTable(String tableName){
        return String.format("DROP TABLE IF EXISTS %s", tableName);
    }

    public static String insertToTable(String tableName){
        return String.format("INSERT IGNORE INTO %s " +
                        "(restaurantId, name, description, popularity, image, price) " +
                        "VALUES(?,?,?,?,?,?)",
                tableName);
    }
}
