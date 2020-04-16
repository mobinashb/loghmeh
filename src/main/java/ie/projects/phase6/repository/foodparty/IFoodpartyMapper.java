package ie.projects.phase6.repository.foodparty;

import ie.projects.phase6.repository.dao.FoodDAO;
import ie.projects.phase6.repository.mapper.IMapper;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IFoodpartyMapper extends IMapper<FoodDAO, String> {

    ArrayList<FoodDAO> getParty(String foodTableName) throws SQLException;
}
