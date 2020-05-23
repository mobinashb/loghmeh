package loghmeh.repository.foodparty;

import loghmeh.repository.food.FoodDAO;
import loghmeh.repository.mapper.IMapper;
import loghmeh.repository.order.OrderDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IFoodpartyMapper extends IMapper<FoodDAO, Object[], String> {

    ArrayList<FoodDAO> getParty(String foodTableName) throws SQLException;
    void updateFoodCount(String restaurantId, ArrayList<OrderDAO> orders) throws SQLException;
}
