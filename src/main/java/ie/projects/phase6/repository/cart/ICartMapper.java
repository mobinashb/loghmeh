package ie.projects.phase6.repository.cart;

import ie.projects.phase6.repository.mapper.IMapper;

import java.sql.SQLException;

public interface ICartMapper extends IMapper<CartDAO, Integer> {
    boolean checkRestaurantEqualityForCart(int cartId, String restaurantId) throws SQLException;
}