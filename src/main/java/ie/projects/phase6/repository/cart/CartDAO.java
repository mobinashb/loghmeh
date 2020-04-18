package ie.projects.phase6.repository.cart;

public class CartDAO {
    private int cartId;
    private String userId;
    private String restaurantId;

    public CartDAO(int cartId, String userId, String restaurantId){
        this.cartId = cartId;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public int getCartId() {
        return cartId;
    }

    public String getUserId() {
        return userId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }
}
