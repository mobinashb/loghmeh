package loghmeh.domain.repeatedTasks;

import loghmeh.domain.FoodpartyManager;
import loghmeh.domain.LoghmehManger;
import loghmeh.domain.foreignServiceObjects.Restaurant;
import loghmeh.repository.food.FoodRepository;
import loghmeh.repository.foodparty.FoodpartyRepository;
import loghmeh.repository.restaurant.RestaurantRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TimerTask;

public class UpdateFoodParty extends TimerTask {
    public void run() {
        try {
            FoodpartyManager foodpartyManager = FoodpartyManager.getInstance();
            foodpartyManager.deleteTable();
            foodpartyManager.createTable();
            ArrayList<Restaurant> restaurants = LoghmehManger.getInstance().getNewRestaurants(true);
            RestaurantRepository.getInstance().addRestaurants(restaurants);
            FoodRepository.getInstance().addFoods(restaurants, true);
            FoodpartyRepository.getInstance().addParty(restaurants);
            foodpartyManager.setLastFoodpartyUpdateTime(System.currentTimeMillis());
        }
        catch (SQLException e1){
            System.out.println("Can't update foodparty table");
            e1.printStackTrace();
        }
    }
}
