//package ie.projects.phase1.services.foods;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectWriter;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.fasterxml.jackson.databind.ser.FilterProvider;
//import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
//import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
//import ie.projects.phase1.core.Loghmeh;
//import ie.projects.phase1.core.Restaurant;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.util.Collections;
//
//@RestController
//public class FoodHandler {
//    Loghmeh loghmeh = Loghmeh.getInstance();
//    ObjectMapper mapper = new ObjectMapper();
//
//
//    @RequestMapping(value = "/v1/restaurants/{restaurantId}/{foodId}", method = RequestMethod.GET)
//    public String getFood(@PathVariable(value = "restaurantId") String restaurantId, @PathVariable(value = "foodId") String foodId) throws IOException{
//        String[] foodFilterParams = {"count", "oldPrice"};
//        Restaurant restaurant = loghmeh.findRestaurantById(restaurantId);
//        return foodJsonCreator(restaurant, foodFilterParams, restaurantId, foodId);
//    }
//
//    @RequestMapping(value = "/v1/partyRestaurants/{restaurantId}/{foodId}", method = RequestMethod.GET)
//    public String getFoodInParty(@PathVariable(value = "restaurantId") String restaurantId, @PathVariable(value = "foodId") String foodId) throws IOException{
//        String[] foodFilterParams = {};
//        Restaurant restaurant = loghmeh.findRestaurantInPartyById(restaurantId);
//        return foodJsonCreator(restaurant, foodFilterParams, restaurantId, foodId);
//    }
//
//    private String foodJsonCreator(Restaurant restaurant, String[] filterParams, String restaurantId, String foodId) throws IOException{
//        FilterProvider filter = new SimpleFilterProvider()
//                .addFilter("food", SimpleBeanPropertyFilter.serializeAllExcept(filterParams));
//        ObjectWriter writer = mapper.writer(filter);
//        String result = writer.writeValueAsString(restaurant.findFoodById(foodId));
//        ObjectNode node = (ObjectNode) mapper.readTree(result);
//        node.put("restaurantName", restaurant.getName());
//        return writer.writeValueAsString(node);
//    }
//
//}