package ie.projects.phase1.core;

import com.google.gson.Gson;
import ie.projects.phase1.exceptions.CartValidationException;
import ie.projects.phase1.server.jsonCreator.JSONStringCreator;
import java.util.ArrayList;

public class Cart {
    private Integer id = 1;
    private ArrayList<Order> orders = new ArrayList<>();
    private ArrayList<Order> partyOrders = new ArrayList<>();
    private String restaurantId;
    private String restaurantName;
    private String deliveryManId;
    private String orderStatus;
    private long deliveryManFoundedTime;
    private double deliveryManTimeToReach;
    private double remainingTimeToDeliver;

    public Cart(){}

    public Cart(Integer id, ArrayList<Order> orders, ArrayList<Order> partyOrders, String restaurantId, String restaurantName, String deliveryManId, String orderStatus, long deliveryManFoundedTime, double deliveryManTimeToReach){
        this.id = id;
        this.orders = orders;
        this.partyOrders = partyOrders;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.deliveryManId = deliveryManId;
        this.orderStatus = orderStatus;
        this.deliveryManFoundedTime = deliveryManFoundedTime;
        this.deliveryManTimeToReach = deliveryManTimeToReach;
        this.remainingTimeToDeliver = 0;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public ArrayList<Order> getPartyOrders() {
        return partyOrders;
    }

    public int getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getRestaurantId() { return restaurantId; }
    public void setRestaurantId(String restaurantId) { this.restaurantId = restaurantId; }

    public String getRestaurantName() { return restaurantName; }

    public String getDeliveryManId() { return deliveryManId; }
    public void setDeliveryManId(String deliveryManId) { this.deliveryManId = deliveryManId; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    public long getDeliveryManFoundedTime() { return deliveryManFoundedTime; }
    public void setDeliveryManFoundedTime(long deliveryManFoundedTime) { this.deliveryManFoundedTime = deliveryManFoundedTime; }

    public double getDeliveryManTimeToReach() { return deliveryManTimeToReach; }
    public void setDeliveryManTimeToReach(double deliveryManTimeToReach) { this.deliveryManTimeToReach = deliveryManTimeToReach; }

    public double getRemainingTimeToDeliver() { return remainingTimeToDeliver; }

    public void setRemainingTimeToDeliver(double remainingTimeToDeliver) { this.remainingTimeToDeliver = remainingTimeToDeliver; }

    private Order findOrderByFoodName(String foodName, ArrayList<Order> orders){
        for(Order order: orders){
            if(order.getFoodName().equals(foodName))
                return order;
        }
        return null;
    }

    private void addToCartUtil(ArrayList<Order> newOrders, String foodName, int number, double price, boolean isNew) throws CartValidationException{
        Order foundedOrder = findOrderByFoodName(foodName, newOrders);
        if (foundedOrder != null) {
            int foodNum = foundedOrder.getFoodNum();
            if (foodNum + number == 0)
                newOrders.remove(foundedOrder);
            else if(foodNum + number < 0)
                throw new CartValidationException(new JSONStringCreator().msgCreator("تعداد درخواستی برای حذف، بیشتر از تعداد انتخاب شده می‌باشد."));
            else
                foundedOrder.setFoodNum(foodNum + number);
        }
        else {
            if(isNew == false)
                throw new CartValidationException(new JSONStringCreator().msgCreator("غذای درخواست‌شده برای تغییر، موجود نمی‌باشد."));
            else {
                if(number <= 0)
                    throw new CartValidationException(new JSONStringCreator().msgCreator("لطفا عدد مثبتی را وارد نمایید."));
                Order newOrder = new Order(foodName, number, price);
                newOrders.add(newOrder);
            }
        }
    }

    public void addToCart(String foodName, int number, double price, String restaurantId, String restaurantName, boolean isParty, boolean isNew) throws CartValidationException{
        if(number == 0)
            throw new CartValidationException(new JSONStringCreator().msgCreator("لطفا عدد دیگری غیر از ۰ وارد کنید."));
        if(isParty) {
            this.addToCartUtil(this.partyOrders, foodName, number, price, isNew);
        }
        else {
            this.addToCartUtil(this.orders, foodName, number, price, isNew);
        }
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        if((this.orders.size() == 0) && (this.partyOrders.size() == 0))
            this.clearOrders();
    }

    public void clearOrders(){
        restaurantId = null;
        restaurantName = null;
        deliveryManId = null;
        orderStatus = null;
        orders.clear();
        partyOrders.clear();
    }
}
