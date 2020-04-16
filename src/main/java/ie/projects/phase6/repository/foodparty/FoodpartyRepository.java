package ie.projects.phase6.repository.foodparty;

import ie.projects.phase6.domain.core.Restaurant;
import ie.projects.phase6.repository.dao.FoodDAO;
import ie.projects.phase6.repository.dao.FoodpartyDAO;
import ie.projects.phase6.utilities.Converter;

import java.sql.SQLException;
import java.util.ArrayList;

public class FoodpartyRepository {
    private static FoodpartyRepository instance;
    private IFoodpartyMapper mapper;

    private FoodpartyRepository() throws SQLException
    {
        mapper = FoodpartyMapper.getInstance();
        mapper.createTable();
    }

    public static FoodpartyRepository getInstance() throws SQLException{
        if (instance == null)
            instance = new FoodpartyRepository();
        return instance;
    }

    public void addParty(ArrayList<Restaurant> restaurants) throws SQLException{
        ArrayList<FoodpartyDAO> foods = Converter.convertToFoodpartyDAO(restaurants);
        mapper.insertAll(foods);
    }

    public ArrayList<FoodpartyDAO> getParty() throws SQLException{
        return mapper.getParty();
    }
}

