package ie.projects.phase6.service.cart.request;

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

    public boolean getIsParty() {
        return isParty;
    }
}
