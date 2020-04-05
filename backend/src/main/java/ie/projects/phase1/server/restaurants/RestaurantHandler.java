package ie.projects.phase1.server.restaurants;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import ie.projects.phase1.core.Loghmeh;
import ie.projects.phase1.core.Restaurant;
import ie.projects.phase1.exceptions.NoRestaurantsAround;
import ie.projects.phase1.exceptions.RestaurantNotFound;
import ie.projects.phase1.server.jsonCreator.JSONStringCreator;
import ie.projects.phase1.server.repeatedTasks.UpdatePartyRestaurants;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

@RestController
public class RestaurantHandler {
    Loghmeh loghmeh = Loghmeh.getInstance();
    ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void test(){
        Timer timer = new Timer();
        timer.schedule(new UpdatePartyRestaurants(), 0, 600000);
    }

    @RequestMapping(value = "/v1/restaurants", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getAllRestaurants() throws IOException, NoRestaurantsAround {
        String[] restaurantFilterParams = {"location", "menu", "estimatedDeliverTime"};
        FilterProvider filter = new SimpleFilterProvider()
                .addFilter("restaurant", SimpleBeanPropertyFilter.serializeAllExcept(restaurantFilterParams));
        ObjectWriter writer = mapper.writer(filter);
        ArrayList<Restaurant> restaurants = loghmeh.getRestaurantsInArea();
        if(restaurants.size() == 0)
            throw new NoRestaurantsAround(new JSONStringCreator().errorMsgCreator("There isn't any restaurant around you"));
        return writer.writeValueAsString(restaurants);
    }

    @RequestMapping(value = "/v1/restaurants/{restaurantId}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getRestaurant(@PathVariable(value = "restaurantId") String restaurantId) throws IOException, RestaurantNotFound{
        String[] restaurantFilterParams = {"location", "estimatedDeliverTime"};
        FilterProvider filter = new SimpleFilterProvider()
                .addFilter("restaurant", SimpleBeanPropertyFilter.serializeAllExcept(restaurantFilterParams));
        ObjectWriter writer = mapper.writer(filter);
        Restaurant restaurant = loghmeh.findRestaurantById(restaurantId);
        if(restaurant == null){
            throw new RestaurantNotFound(new JSONStringCreator().errorMsgCreator("There isn't any restaurant with id " + restaurantId));
        }
        return writer.writeValueAsString(loghmeh.findRestaurantById(restaurantId));
    }

    @RequestMapping(value = "/v1/partyRestaurants", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getAllRestaurantsInParty() throws IOException, NoRestaurantsAround {
        String[] restaurantFilterParams = {"location", "logo", "estimatedDeliverTime"};
        FilterProvider filter = new SimpleFilterProvider()
                .addFilter("restaurant", SimpleBeanPropertyFilter.serializeAllExcept(restaurantFilterParams));
        ObjectWriter writer = mapper.writer(filter);
        ArrayList<Restaurant> restaurants = loghmeh.getRestaurantsInParty();
        if(restaurants.size() == 0)
            throw new NoRestaurantsAround(new JSONStringCreator().errorMsgCreator("There isn't any party food around you"));
        return writer.writeValueAsString(restaurants);
    }
}