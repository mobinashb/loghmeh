package ie.projects.phase6.repository.food;

import ie.projects.phase6.domain.core.Food;
import ie.projects.phase6.domain.core.Restaurant;
import ie.projects.phase6.repository.ConnectionPool;
import ie.projects.phase6.repository.food.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FoodRepository {

    private static FoodRepository instance;
    private static final String TABLE_NAME = "FOOD";

    private FoodRepository() throws SQLException
    {
        Connection con = ConnectionPool.getConnection();
        Statement statement = con.createStatement();
        statement.executeUpdate(mapper.deleteExistingTable(TABLE_NAME));
        statement.executeUpdate(mapper.createTable(TABLE_NAME));
        statement.close();
        con.close();
    }

    public static FoodRepository getInstance() throws SQLException{
        if (instance == null)
            instance = new FoodRepository();
        return instance;
    }

    public void addFoods(ArrayList<Restaurant> restaurants) throws SQLException{
        String sql = mapper.insertToTable(TABLE_NAME);
        Connection con = ConnectionPool.getConnection();
        PreparedStatement statement = con.prepareStatement(sql);
        con.setAutoCommit(false);
        for (Restaurant restaurant: restaurants){
            for (Food food: restaurant.getMenu()) {
                food.setRestaurantId(restaurant.getId());
                addFood(food, statement);
            }
        }
        statement.executeBatch();
        con.commit();
        statement.close();
        con.close();
    }

    private void addFood(Food food, PreparedStatement statement) throws SQLException{
        statement.setString(1, food.getRestaurantId());
        statement.setString(2, food.getName());
        statement.setString(3, food.getDescription());
        statement.setFloat(4, food.getPopularity());
        statement.setString(5, food.getImage());
        statement.setFloat(6, food.getPrice());
        statement.addBatch();
    }
}








