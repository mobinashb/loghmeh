package ie.projects.phase7.utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ie.projects.phase7.domain.RestaurantManager;
import ie.projects.phase7.repository.finalizedCart.FinalizedCartDAO;
import ie.projects.phase7.repository.order.OrderDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class JsonStringCreator {
    public static String msgCreator(String msg){
        return "{\"msg\": " + "\"" + msg + "\"}";
    }

    public static String foodpartyJson(JsonNode foodparty, double remainingTime){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.putPOJO("foodparty", foodparty);
        objectNode.put("remainingTime", remainingTime);
        return objectNode.toString();
    }

    public static String getCartJson(FinalizedCartDAO cart, ArrayList<OrderDAO> orders) throws SQLException {
        JsonArray ordersArray = new JsonArray();
        JsonObject result = new JsonObject();
        result.addProperty("restaurantId", cart.getRestaurantId());
        result.addProperty("restaurantName", RestaurantManager.getInstance().getRestaurantsName(new ArrayList<String>(Arrays.asList(cart.getRestaurantId()))).get(0));
        result.addProperty("id", cart.getCartId());
        for(OrderDAO order: orders){
            JsonObject orderObj = new JsonObject();
            orderObj.addProperty("foodName", order.getFoodName());
            orderObj.addProperty("number", order.getFoodNum());
            orderObj.addProperty("price", order.getPrice());
            orderObj.addProperty("isParty", order.getIsParty());
            ordersArray.add(orderObj);
        }
        result.add("orders", ordersArray);
        return result.toString();
    }

}
