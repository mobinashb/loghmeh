package loghmeh.repository.restaurant;

import loghmeh.domain.foreignServiceObjects.Restaurant;
import loghmeh.repository.food.FoodMapper;
import loghmeh.utilities.Converter;

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
        System.out.println("FETCHED RESTAURANT: " + restaurantsDAO.get(0).getName());
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








