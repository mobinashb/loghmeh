package ie.projects.phase6.repository.cart;

import java.sql.SQLException;

public class CartRepository {

    private static CartRepository instance;
    private ICartMapper mapper;

    private CartRepository() throws SQLException
    {
        mapper = CartMapper.getInstance();
        mapper.createTable();
    }

    public static CartRepository getInstance() throws SQLException{
        if (instance == null)
            instance = new CartRepository();
        return instance;
    }

    public void addNewCart(int cartId, String userId, String restaurantId) throws SQLException{
        this.mapper.insert(new CartDAO(cartId, userId, restaurantId));
    }

    public boolean checkRestaurantEqualityForCart(int cartId, String restaurantId) throws SQLException{
        return this.mapper.checkRestaurantEqualityForCart(cartId, restaurantId);
    }
    public void delete(int cartId) throws SQLException{
        this.mapper.delete(new Integer(cartId));
    }

    public CartDAO findCartById(int cartId) throws SQLException{
        try {
            return this.mapper.find(new Integer(cartId));
        }
        catch (SQLException e1){
            return null;
        }
    }

    public CartDAO getCartByUserId(String userId) throws SQLException{
        return this.mapper.getCartByUserId(userId);
    }

}
