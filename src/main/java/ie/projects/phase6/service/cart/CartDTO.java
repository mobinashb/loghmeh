package ie.projects.phase6.service.cart;

import java.util.ArrayList;

public class CartDTO {
    int cartId;
    String restaurantId;
    String restaurantName;
    ArrayList<OrderDTO> orders;

    public CartDTO(int cartId, String restaurantId, String restaurantName, ArrayList<OrderDTO> orders){
        this.cartId = cartId;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.orders = orders;
    }

    public int getCartId() {
        return cartId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public ArrayList<OrderDTO> getOrders() {
        return orders;
    }
}
