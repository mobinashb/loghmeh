package ie.projects.phase1.server.jsonCreator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ie.projects.phase1.core.Cart;
import ie.projects.phase1.core.Order;

import java.util.ArrayList;

public class JSONStringCreator {
    public String msgCreator(String msg){
        return "{\"msg\": " + "\"" + msg + "\"}";
    }

    public JsonObject cartCreator(Cart cart){
        JsonArray orders = new JsonArray();
        JsonObject tempObj = new JsonObject();
        tempObj.addProperty("restaurantId", cart.getRestaurantId());
        tempObj.addProperty("restaurantName", cart.getRestaurantName());
        tempObj.addProperty("id", cart.getId());
        for(Order order: cart.getOrders()){
            JsonObject orderObj = new JsonObject();
            orderObj.addProperty("foodName", order.getFoodName());
            orderObj.addProperty("number", order.getFoodNum());
            orderObj.addProperty("price", order.getPrice());
            orderObj.addProperty("isParty", 0);
            orders.add(orderObj);
        }
        for(Order order: cart.getPartyOrders()){
            JsonObject orderObj = new JsonObject();
            orderObj.addProperty("foodName", order.getFoodName());
            orderObj.addProperty("number", order.getFoodNum());
            orderObj.addProperty("price", order.getPrice());
            orderObj.addProperty("isParty", 1);
            orders.add(orderObj);
        }
        tempObj.add("orders", orders);
        return tempObj;
    }

    public String ordersCreator(ArrayList<Cart> carts){
        JsonArray orders = new JsonArray();
        for (Cart cart: carts){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", cart.getId());
            jsonObject.addProperty("restaurantName", cart.getRestaurantName());
            jsonObject.addProperty("orderStatus", cart.getOrderStatus());
            orders.add(jsonObject);
        }
        return orders.toString();
    }

    public String partyfoodJson(JsonNode partyfoods, double remainingTime){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.putPOJO("restaurants", partyfoods);
        objectNode.put("remainingTime", remainingTime);
        return objectNode.toString();
    }
}
