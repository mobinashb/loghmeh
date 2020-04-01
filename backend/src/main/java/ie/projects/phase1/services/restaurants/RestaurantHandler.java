package ie.projects.phase1.services.restaurants;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.gson.Gson;
import ie.projects.phase1.core.Food;
import ie.projects.phase1.core.GeoLocation;
import ie.projects.phase1.core.Loghmeh;
import ie.projects.phase1.core.Restaurant;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class RestaurantHandler {
    Loghmeh loghmeh = Loghmeh.getInstance();
    Gson gson = new Gson();
    ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/v1/restaurants", method = RequestMethod.GET)
    public String getAllRestaurants() throws IOException {
        String[] filterParams = {"id" ,"location", "menu", "estimatedDeliverTime"};
        FilterProvider filter = new SimpleFilterProvider().addFilter("restaurant", SimpleBeanPropertyFilter.serializeAllExcept(filterParams));
        ObjectWriter writer = mapper.writer(filter);
        return writer.writeValueAsString(loghmeh.getRestaurantsInArea());
    }

    @RequestMapping(value = "/v1/restaurants/{restaurantId}", method = RequestMethod.GET)
    public String getRestaurant(@PathVariable(value = "restaurantId") String restaurantId) throws IOException{
        return mapper.writeValueAsString(loghmeh.findRestaurantById(restaurantId));
    }

    @RequestMapping(value = "/v1/partyRestaurants", method = RequestMethod.GET)
    public String getAllRestaurantsInParty() throws IOException{
        return mapper.writeValueAsString(loghmeh.getRestaurantsInParty());
    }

    @RequestMapping(value = "/v1/partyRestaurants/{restaurantId}", method = RequestMethod.GET)
    public String getRestaurantInParty(@PathVariable(value = "restaurantId") String restaurantId) throws IOException{
        return mapper.writeValueAsString(loghmeh.findRestaurantInPartyById(restaurantId));
    }
}
