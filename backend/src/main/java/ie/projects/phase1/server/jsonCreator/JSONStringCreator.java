package ie.projects.phase1.server.jsonCreator;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ie.projects.phase1.core.Cart;
import ie.projects.phase1.core.Loghmeh;

import java.util.Map;

public class JSONStringCreator {
    public String errorMsgCreator(String msg){
        return "{\"msg\": " + "\"" + msg + "\"}";
    }

    public JsonObject cartCreator(Cart cart){
        Loghmeh loghmeh = Loghmeh.getInstance();
        JsonArray orders = new JsonArray();
        JsonObject tempObj = new JsonObject();
        tempObj.addProperty("restaurantId", cart.getRestaurantId());
        tempObj.addProperty("restaurantName", loghmeh.findRestaurantById(cart.getRestaurantId()).getName());
        tempObj.addProperty("id", cart.getId());
        for(Map.Entry element: cart.getOrders().entrySet()){
            JsonObject orderObj = new JsonObject();
            orderObj.addProperty("foodName", (String) element.getKey());
            orderObj.addProperty("number", (int) element.getValue());
            orderObj.addProperty("price", loghmeh.findRestaurantById(cart.getRestaurantId()).findFood((String) element.getKey()).getPrice());
            orderObj.addProperty("isParty", 0);
            orders.add(orderObj);
        }
        for(Map.Entry element: cart.getPartyOrders().entrySet()){
            JsonObject orderObj = new JsonObject();
            orderObj.addProperty("foodName", (String) element.getKey());
            orderObj.addProperty("number", (int) element.getValue());
            orderObj.addProperty("price", loghmeh.findRestaurantInPartyById(cart.getRestaurantId()).findFood((String) element.getKey()).getPrice());
            orderObj.addProperty("isParty", 1);
            orders.add(orderObj);
        }
        tempObj.add("orders", orders);
        return tempObj;
    }
}
