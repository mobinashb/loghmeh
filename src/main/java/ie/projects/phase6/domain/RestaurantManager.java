package ie.projects.phase6.domain;

import ie.projects.phase6.domain.core.Restaurant;
import ie.projects.phase6.repository.dao.RestaurantDAO;
import ie.projects.phase6.repository.food.FoodRepository;
import ie.projects.phase6.repository.restaurant.RestaurantRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class RestaurantManager {
    private static RestaurantManager instance;

    private RestaurantRepository restaurantRepository;

    private RestaurantManager() throws SQLException{
        this.restaurantRepository = RestaurantRepository.getInstance();
        ArrayList<Restaurant> restaurants = LoghmehManger.getInstance().getNewRestaurants(false);
        this.restaurantRepository.addRestaurants(restaurants);
        FoodRepository.getInstance().addFoods(restaurants);
    }

    public static RestaurantManager getInstance() throws SQLException {
        if (instance == null)
            instance = new RestaurantManager();
        return instance;
    }

    public ArrayList<RestaurantDAO> getRestaurants(int pageNumber, int pageSize){
        return this.restaurantRepository.getRestaurants(pageNumber, pageSize);
    }

    public RestaurantDAO getRestaurantById(String restaurantId) throws SQLException{
        return this.restaurantRepository.getRestaurantById(restaurantId);
    }
}
