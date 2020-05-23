package loghmeh.service.cart.response;

public class FinalizedCartsDTO {
    Integer cartId;
    String restaurantId;
    String restaurantName;
    int orderStatus;

    public FinalizedCartsDTO(Integer cartId, String restaurantId, String restaurantName, int orderStatus){
        this.cartId = cartId;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.orderStatus = orderStatus;
    }

    public Integer getCartId() {
        return cartId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public int getOrderStatus() {
        return orderStatus;
    }
}
