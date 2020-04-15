package ie.projects.phase6.repository.restaurant;

import ie.projects.phase6.repository.dao.RestaurantDAO;
import ie.projects.phase6.repository.mapper.IMapper;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IRestaurantMapper extends IMapper<RestaurantDAO, String> {

    ArrayList<RestaurantDAO> getContainsText(String text) throws SQLException;

}
