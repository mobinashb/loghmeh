package ie.projects.phase6.repository.cart;

import ie.projects.phase6.repository.mapper.IMapper;

import java.sql.SQLException;

public interface ICartMapper extends IMapper<CartDAO, Integer, String> {
    boolean checkRestaurantEqualityForCart(int cartId, String restaurantId) throws SQLException;
    CartDAO getCartByUserId(String userId) throws SQLException;
}