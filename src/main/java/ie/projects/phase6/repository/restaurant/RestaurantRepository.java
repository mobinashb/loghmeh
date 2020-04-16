package ie.projects.phase6.repository.restaurant;

import ie.projects.phase6.domain.core.Restaurant;
import ie.projects.phase6.repository.dao.RestaurantDAO;
import ie.projects.phase6.utilities.Converter;

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
}








