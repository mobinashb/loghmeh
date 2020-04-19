package ie.projects.phase6.repository.restaurant;

import ie.projects.phase6.repository.mapper.IMapper;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IRestaurantMapper extends IMapper<RestaurantDAO, String, String> {

    ArrayList<RestaurantDAO> getRestaurantsByPaging(int pageNumber, int pageSize);
    ArrayList<String> getRestaurantsNameById(ArrayList<String> restaurantsId) throws SQLException;
}
