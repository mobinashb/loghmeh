package ie.projects.phase6.domain.core;

public class Order {
    private String foodName;
    private int foodNum;
    private double price;

    public Order(String foodName, int foodNum, double price){
        this.foodName = foodName;
        this.foodNum = foodNum;
        this.price = price;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getFoodNum() {
        return foodNum;
    }

    public void setFoodNum(int foodNum) {
        this.foodNum = foodNum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
