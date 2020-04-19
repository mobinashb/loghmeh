package ie.projects.phase6.repository.foodparty;

import ie.projects.phase6.domain.core.Restaurant;
import ie.projects.phase6.repository.food.FoodDAO;
import ie.projects.phase6.repository.food.FoodMapper;
import ie.projects.phase6.utilities.Converter;

import java.sql.SQLException;
import java.util.ArrayList;

public class FoodpartyRepository {
    private static FoodpartyRepository instance;
    private IFoodpartyMapper mapper;

    private FoodpartyRepository() throws SQLException
    {
        mapper = FoodpartyMapper.getInstance();
    }

    public static FoodpartyRepository getInstance() throws SQLException{
        if (instance == null)
            instance = new FoodpartyRepository();
        return instance;
    }

    public void deleteTable() throws SQLException{
        mapper.deleteTable();
    }
    public void createTable() throws SQLException{
        mapper.createTable();
    }

    public void addParty(ArrayList<Restaurant> restaurants) throws SQLException{
        ArrayList<FoodDAO> foods = Converter.convertToFoodpartyDAO(restaurants);
        mapper.insertAll(foods);
    }

    public ArrayList<FoodDAO> getParty() throws SQLException{
        return mapper.getParty(FoodMapper.getTableName());
    }

    public FoodDAO findFoodpartyById(String restaurantId, String foodName) throws SQLException{
        Object[] id = new Object[2];
        id[0] = restaurantId;
        id[1] = foodName;
        return this.mapper.find(id);
    }

    public void updateFoodpartyCount(String restaurantId, String foodName, int foodNum) throws SQLException{
        Object[] id = new Object[2];
        id[0] = restaurantId;
        id[1] = foodName;
        this.mapper.updateFoodCount(id, foodNum);
    }

}
