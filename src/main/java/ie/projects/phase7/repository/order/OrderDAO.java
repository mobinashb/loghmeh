package ie.projects.phase7.repository.order;

public class OrderDAO {
    int cartId;
    String foodName;
    int foodNum;
    float price;
    boolean isParty;

    public OrderDAO(int cartId, String foodName, int foodNum, float price, boolean isParty){
        this.cartId = cartId;
        this.foodName = foodName;
        this.foodNum = foodNum;
        this.price = price;
        this.isParty = isParty;
    }

    public int getCartId() {
        return cartId;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getFoodNum() {
        return foodNum;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean getIsParty(){
        return this.isParty;
    }
}
