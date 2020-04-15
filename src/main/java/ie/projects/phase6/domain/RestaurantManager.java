package ie.projects.phase6.domain;

import ie.projects.phase6.domain.core.Restaurant;
import ie.projects.phase6.repository.food.FoodRepository;
import ie.projects.phase6.repository.restaurant.RestaurantRepository;
import ie.projects.phase6.utilities.Converter;

import java.sql.SQLException;
import java.util.ArrayList;

public class RestaurantManager {
    private static RestaurantManager instance;

    private RestaurantManager() throws SQLException{
        RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();
        ArrayList<Restaurant> restaurants = LoghmehManger.getInstance().getNewRestaurants();
        restaurantRepository.addRestaurants(restaurants);
//        FoodRepository.getInstance().addFoods(restaurants);
    }

    public static RestaurantManager getInstance() throws SQLException {
        if (instance == null)
            instance = new RestaurantManager();
        return instance;
    }
}
