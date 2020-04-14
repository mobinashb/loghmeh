package ie.projects.phase6.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class RestaurantRepository {
    private static RestaurantRepository instance;
    private static final String TABLE_NAME = "RESTAURANT";

    private RestaurantRepository()
    {
        try {
            Connection con = ConnectionPool.getConnection();
            Statement statement = con.createStatement();
            statement.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            statement.executeUpdate(String.format(
                    "CREATE TABLE  %s " +
                            "(" +
                            "id integer PRIMARY KEY, " +
                            "text TEXT" +
                            ");",
                    TABLE_NAME));
            statement.close();
            con.close();
        }catch (SQLException e1){
            e1.printStackTrace();
        }
    }

    public static RestaurantRepository getInstance() {
        if (instance == null)
            instance = new RestaurantRepository();
        return instance;
    }
}
