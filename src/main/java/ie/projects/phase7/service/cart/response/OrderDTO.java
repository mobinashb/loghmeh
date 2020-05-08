package ie.projects.phase7.service.cart.response;

public class OrderDTO {
    String foodName;
    int number;
    float price;
    boolean isParty;

    public OrderDTO(String foodName, int number, float price, boolean isParty){
        this.foodName = foodName;
        this.number = number;
        this.price = price;
        this.isParty = isParty;
    }

    public String getFoodName() {
        return foodName;
    }

    public float getPrice() {
        return price;
    }

    public int getNumber() {
        return number;
    }

    public boolean getIsParty() {
        return isParty;
    }
}
