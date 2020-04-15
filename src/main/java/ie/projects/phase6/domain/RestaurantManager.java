package ie.projects.phase6.domain;

import ie.projects.phase6.repository.restaurant.RestaurantRepository;
import java.sql.SQLException;

public class RestaurantManager {
    private static RestaurantManager instance;

    private RestaurantManager() throws SQLException{
        RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();
        restaurantRepository.addRestaurants(LoghmehManger.getInstance().getNewRestaurants());
    }

    public static RestaurantManager getInstance() throws SQLException {
        if (instance == null)
            instance = new RestaurantManager();
        return instance;
    }
}
