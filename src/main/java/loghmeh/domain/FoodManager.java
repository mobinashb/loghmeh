package loghmeh.domain;

import loghmeh.domain.exceptions.RestaurantNotFound;
import loghmeh.repository.food.FoodDAO;
import loghmeh.repository.food.FoodRepository;
import loghmeh.utilities.JsonStringCreator;

import java.sql.SQLException;
import java.util.ArrayList;

public class FoodManager {
    private static FoodManager instance;

    private FoodRepository foodRepository;

    private FoodManager() throws SQLException{
        this.foodRepository = FoodRepository.getInstance();
    }

    public static FoodManager getInstance() throws SQLException {
        if (instance == null)
            instance = new FoodManager();
        return instance;
    }

    public ArrayList<FoodDAO> getFoods(String restaurantId) throws RestaurantNotFound{
        try {
            return this.foodRepository.getFoods(restaurantId);
        }
        catch (SQLException e1){
            throw new RestaurantNotFound(JsonStringCreator.msgCreator("رستورانی با شناسه درخواست شده موجود نمی باشد"));
        }
    }

    public FoodDAO findFood(String restaurantId, String foodName){
        try {
            return this.foodRepository.findFood(restaurantId, foodName);
        }
        catch (SQLException e1){
            return null;
        }
    }
}
