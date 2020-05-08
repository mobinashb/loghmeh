package ie.projects.phase7.repository.finalizedCart;

public class FinalizedCartDAO {
    private int cartId;
    private String userId;
    private String restaurantId;
    private String deliveryManId;
    private int orderStatus;
    private long deliveryManFoundedTime;
    private double deliveryManTimeToReach;
    private double remainingTimeToDeliver;

    public FinalizedCartDAO(int cartId, String userId, String restaurantId, String deliveryManId, int orderStatus, long deliveryManFoundedTime, double deliveryManTimeToReach){
        this.cartId = cartId;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.deliveryManId = deliveryManId;
        this.orderStatus = orderStatus;
        this.deliveryManFoundedTime = deliveryManFoundedTime;
        this.deliveryManTimeToReach = deliveryManTimeToReach;
        this.remainingTimeToDeliver = 0;
    }

    public FinalizedCartDAO(int cartId, String userId, String restaurantId, String deliveryManId){
        this.cartId = cartId;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.deliveryManId = deliveryManId;
    }

    public FinalizedCartDAO(int cartId, String restaurantId, int orderStatus){
        this.cartId = cartId;
        this.restaurantId = restaurantId;
        this.orderStatus = orderStatus;
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

    public String getDeliveryManId() {
        return deliveryManId;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public double getDeliveryManTimeToReach() {
        return deliveryManTimeToReach;
    }

    public double getRemainingTimeToDeliver() {
        return remainingTimeToDeliver;
    }

    public long getDeliveryManFoundedTime() {
        return deliveryManFoundedTime;
    }
}
