package ie.projects.phase1.server.carts;

public class CartRequest {
    private String foodName;
    private int number;
    private String restaurantId;
    private boolean isParty;

    public String getFoodName() {
        return foodName;
    }

    public int getNumber() {
        return number;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public boolean isParty() {
        return isParty;
    }
}
