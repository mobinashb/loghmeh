package loghmeh.repository.restaurant;

import loghmeh.repository.mapper.IMapper;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IRestaurantMapper extends IMapper<RestaurantDAO, String, String> {

    ArrayList<RestaurantDAO> getRestaurantsByPaging(int pageNumber, int pageSize);
    ArrayList<String> getRestaurantsNameById(ArrayList<String> restaurantsId) throws SQLException;
    ArrayList<RestaurantDAO> searchRestaurants(String restaurantName, String foodName, String foodTableName, int pageNumber, int pageSize) throws SQLException;
}
