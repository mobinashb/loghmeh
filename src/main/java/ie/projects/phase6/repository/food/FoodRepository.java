package ie.projects.phase6.repository.food;

import ie.projects.phase6.domain.core.Restaurant;
import ie.projects.phase6.utilities.Converter;

import java.sql.SQLException;
import java.util.ArrayList;

public class FoodRepository {

    private static FoodRepository instance;
    private IFoodMapper mapper;

    private FoodRepository() throws SQLException
    {
        mapper = FoodMapper.getInstance();
        mapper.createTable();
    }

    public static FoodRepository getInstance() throws SQLException{
        if (instance == null)
            instance = new FoodRepository();
        return instance;
    }

    public void addFoods(ArrayList<Restaurant> restaurants, boolean isParty) throws SQLException{
        ArrayList<FoodDAO> foods = Converter.convertToFoodDAO(restaurants, isParty);
        mapper.insertAll(foods);
    }

    public ArrayList<FoodDAO> getFoods(String restaurantId) throws SQLException{
        return mapper.findAllById(restaurantId);
    }

    public FoodDAO findFood(String restaurantId, String foodName) throws SQLException{
        Object[] id = new Object[2];
        id[0] = restaurantId;
        id[1] = foodName;
        return this.mapper.find(id);
    }

}
