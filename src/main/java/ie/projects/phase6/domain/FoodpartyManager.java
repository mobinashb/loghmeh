package ie.projects.phase6.domain;

import ie.projects.phase6.domain.core.Restaurant;
import ie.projects.phase6.repository.dao.FoodpartyDAO;
import ie.projects.phase6.repository.food.FoodRepository;
import ie.projects.phase6.repository.foodparty.FoodpartyRepository;
import ie.projects.phase6.repository.restaurant.RestaurantRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class FoodpartyManager {
    private static FoodpartyManager instance;

    private FoodpartyRepository foodpartyRepository;

    private FoodpartyManager() throws SQLException{
        this.foodpartyRepository = FoodpartyRepository.getInstance();
        ArrayList<Restaurant> restaurants = LoghmehManger.getInstance().getNewRestaurants(true);
        RestaurantRepository.getInstance().addRestaurants(restaurants);
        FoodRepository.getInstance().addFoods(restaurants);
        this.foodpartyRepository.addParty(restaurants);
    }

    public static FoodpartyManager getInstance() throws SQLException {
        if (instance == null)
            instance = new FoodpartyManager();
        return instance;
    }

    public ArrayList<FoodpartyDAO> getParty() throws SQLException{
        return this.foodpartyRepository.getParty();
    }
}
