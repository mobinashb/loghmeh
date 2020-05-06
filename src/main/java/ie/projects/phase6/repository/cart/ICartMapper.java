package ie.projects.phase6.repository.cart;

import ie.projects.phase6.repository.ConnectionPool;
import ie.projects.phase6.repository.mapper.IMapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface ICartMapper extends IMapper<CartDAO, Integer, String> {
    boolean checkRestaurantEqualityForCart(int cartId, String restaurantId) throws SQLException;
    int getMaxId() throws SQLException;
}