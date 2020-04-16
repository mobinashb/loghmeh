package ie.projects.phase6.utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonStringCreator {
    public static String msgCreator(String msg){
        return "{\"msg\": " + "\"" + msg + "\"}";
    }

    public static String foodpartyJson(JsonNode foodparty, double remainingTime){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.putPOJO("restaurants", foodparty);
        objectNode.put("remainingTime", remainingTime);
        return objectNode.toString();
    }
}
