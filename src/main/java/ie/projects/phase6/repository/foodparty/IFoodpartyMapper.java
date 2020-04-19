package ie.projects.phase6.repository.foodparty;

import ie.projects.phase6.repository.food.FoodDAO;
import ie.projects.phase6.repository.mapper.IMapper;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IFoodpartyMapper extends IMapper<FoodDAO, Object[], String> {

    ArrayList<FoodDAO> getParty(String foodTableName) throws SQLException;
    void updateFoodCount(Object[] id, int foodNum) throws SQLException;
}
