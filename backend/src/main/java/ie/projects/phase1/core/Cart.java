package ie.projects.phase1.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class Cart {
    private Integer id = 1;
    private HashMap<String, Integer> orders = new HashMap<String, Integer>();
    private HashMap<String, Integer> partyOrders = new HashMap<String, Integer>();
    private String restaurantId;
    private String deliveryManId;
    private String orderStatus;
    private long deliveryManFoundedTime;
    private double deliveryManTimeToReach;
    private double remainingTimeToDeliver;

    public Cart(){}

    public Cart(Integer id, HashMap<String, Integer> orders, HashMap<String, Integer> partyOrders, String restaurantId, String deliveryManId, String orderStatus, long deliveryManFoundedTime, double deliveryManTimeToReach){
        this.id = id;
        Gson gson = new Gson();
        String ordersJsonString = gson.toJson(orders);
        String partyOrdersJsonString = gson.toJson(partyOrders);
        Type type = new TypeToken<HashMap<String, Integer> >(){}.getType();
        this.orders = gson.fromJson(ordersJsonString, type);
        this.partyOrders = gson.fromJson(partyOrdersJsonString, type);
        this.restaurantId = restaurantId;
        this.deliveryManId = deliveryManId;
        this.orderStatus = orderStatus;
        this.deliveryManFoundedTime = deliveryManFoundedTime;
        this.deliveryManTimeToReach = deliveryManTimeToReach;
        this.remainingTimeToDeliver = 0;
    }

    public HashMap<String, Integer> getOrders() {
        return orders;
    }

    public HashMap<String, Integer> getPartyOrders() { return partyOrders; }

    public int getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getRestaurantId() { return restaurantId; }
    public void setRestaurantId(String restaurantId) { this.restaurantId = restaurantId; }

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

    public String addNewOrder(String foodName, int number, String restaurantId, boolean isParty) {
        if(isParty)
            this.partyOrders.put(foodName, number);
        else
            this.orders.put(foodName, number);
        this.restaurantId = restaurantId;
        return "Order added";
    }

    public String editCart(String foodName, int number, boolean isParty){
        if(isParty)
            this.partyOrders.put(foodName, this.partyOrders.get(foodName) + number);
        else
            this.orders.put(foodName, this.orders.get(foodName) + number);
        return "Order edited";
    }

    public void clearOrders(){
        restaurantId = null;
        deliveryManId = null;
        orderStatus = null;
        orders.clear();
        partyOrders.clear();
    }
}
