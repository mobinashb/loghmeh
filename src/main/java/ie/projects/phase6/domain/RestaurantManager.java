package ie.projects.phase6.domain;

import ie.projects.phase6.domain.foreignServiceObjects.Restaurant;
import ie.projects.phase6.domain.exceptions.RestaurantNotFound;
import ie.projects.phase6.repository.restaurant.RestaurantDAO;
import ie.projects.phase6.repository.food.FoodRepository;
import ie.projects.phase6.repository.restaurant.RestaurantRepository;
import ie.projects.phase6.utilities.JsonStringCreator;

import java.sql.SQLException;
import java.util.ArrayList;

public class RestaurantManager {
    private static RestaurantManager instance;

    private RestaurantRepository restaurantRepository;

    private RestaurantManager() throws SQLException{
        this.restaurantRepository = RestaurantRepository.getInstance();
        ArrayList<Restaurant> restaurants = LoghmehManger.getInstance().getNewRestaurants(false);
        this.restaurantRepository.addRestaurants(restaurants);
        FoodRepository.getInstance().addFoods(restaurants, false);
    }

    public static RestaurantManager getInstance() throws SQLException {
        if (instance == null)
            instance = new RestaurantManager();
        return instance;
    }

    public ArrayList<RestaurantDAO> getRestaurants(int pageNumber, int pageSize){
        return this.restaurantRepository.getRestaurants(pageNumber, pageSize);
    }

    public RestaurantDAO getRestaurantById(String restaurantId) throws RestaurantNotFound{
        try {
            return this.restaurantRepository.getRestaurantById(restaurantId);
        }
        catch (SQLException e1){
            throw new RestaurantNotFound(JsonStringCreator.msgCreator("رستورانی با شناسه درخواست شده یافت نشد"));
        }
    }

    public ArrayList<String> getRestaurantsName(ArrayList<String> restaurantId) throws SQLException{
        return this.restaurantRepository.getRestaurantsNameById(restaurantId);
    }

    public ArrayList<RestaurantDAO> searchRestaurants(String restaurantName, String foodName){
        try {
            return this.restaurantRepository.searchRestaurants(restaurantName, foodName);
        }
        catch (SQLException e1){
            return new ArrayList<>();
        }
    }

}
