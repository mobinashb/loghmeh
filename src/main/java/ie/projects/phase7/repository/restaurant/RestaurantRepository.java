package ie.projects.phase7.repository.restaurant;

import ie.projects.phase7.domain.foreignServiceObjects.Restaurant;
import ie.projects.phase7.repository.food.FoodMapper;
import ie.projects.phase7.utilities.Converter;

import java.sql.*;
import java.util.ArrayList;

public class RestaurantRepository {
    private static RestaurantRepository instance;
    private IRestaurantMapper mapper;

    private RestaurantRepository() throws SQLException
    {
        mapper = RestaurantMapper.getInstance();
        mapper.createTable();
    }

    public static RestaurantRepository getInstance() throws SQLException{
        if (instance == null)
            instance = new RestaurantRepository();
        return instance;
    }

    public void addRestaurants(ArrayList<Restaurant> restaurants) throws SQLException{
        ArrayList<RestaurantDAO> restaurantsDAO = Converter.convertToRestaurantDAO(restaurants);
        mapper.insertAll(restaurantsDAO);
    }

    public ArrayList<RestaurantDAO> getRestaurants(int pageNumber, int pageSize){
        return mapper.getRestaurantsByPaging(pageNumber, pageSize);
    }

    public RestaurantDAO getRestaurantById(String restaurantId) throws SQLException{
        return mapper.find(restaurantId);
    }

    public ArrayList<String> getRestaurantsNameById(ArrayList<String> id) throws SQLException{
        return mapper.getRestaurantsNameById(id);
    }

    public ArrayList<RestaurantDAO> searchRestaurants(String restaurantName, String foodName, int pageNumber, int pageSize) throws SQLException{
        return mapper.searchRestaurants(restaurantName, foodName, FoodMapper.getTableName(), pageNumber, pageSize);
    }

}








