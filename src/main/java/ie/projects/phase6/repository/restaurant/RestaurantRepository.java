package ie.projects.phase6.repository.restaurant;

import ie.projects.phase6.domain.core.Restaurant;
import ie.projects.phase6.repository.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;

public class RestaurantRepository {
    private static RestaurantRepository instance;
    private static final String TABLE_NAME = "RESTAURANT";

    private RestaurantRepository() throws SQLException
    {
        Connection con = ConnectionPool.getConnection();
        Statement statement = con.createStatement();
        statement.executeUpdate(mapper.deleteExistingTable(TABLE_NAME));
        statement.executeUpdate(mapper.createTable(TABLE_NAME));
        statement.close();
        con.close();
    }

    public static RestaurantRepository getInstance() throws SQLException{
        if (instance == null)
            instance = new RestaurantRepository();
        return instance;
    }

    public void addRestaurants(ArrayList<Restaurant> restaurants) throws SQLException{
        String sql = mapper.insertToTable(TABLE_NAME);
        Connection con = ConnectionPool.getConnection();
        PreparedStatement statement = con.prepareStatement(sql);
        con.setAutoCommit(false);
        for (Restaurant restaurant: restaurants){
            addRestaurant(restaurant, statement);
        }
        statement.executeBatch();
        con.commit();
        statement.close();
        con.close();
    }

    private void addRestaurant(Restaurant restaurant, PreparedStatement statement) throws SQLException{
        statement.setString(1, restaurant.getId());
        statement.setString(2, restaurant.getName());
        statement.setString(3, restaurant.getLogo());
        statement.setDouble(4, restaurant.getLocation().getx());
        statement.setDouble(5, restaurant.getLocation().gety());
        statement.addBatch();
    }
}








