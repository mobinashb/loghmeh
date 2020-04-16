package ie.projects.phase6.service.controller.restaurant;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.projects.phase6.domain.FoodManager;
import ie.projects.phase6.domain.FoodpartyManager;
import ie.projects.phase6.domain.RestaurantManager;
import ie.projects.phase6.domain.core.Restaurant;
import ie.projects.phase6.exceptions.classes.RestaurantNotFound;
import ie.projects.phase6.repository.dao.FoodDAO;
import ie.projects.phase6.repository.dao.FoodpartyDAO;
import ie.projects.phase6.repository.dao.RestaurantDAO;
import ie.projects.phase6.repository.restaurant.RestaurantMapper;
import ie.projects.phase6.utilities.DAO_DTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class RestaurantService {
    ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/v1/restaurants/{pageNum}/{pageSize}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getRestaurants(@PathVariable(value = "pageNum") int pageNumber, @PathVariable(value = "pageSize") int pageSize) throws SQLException, IOException {
        RestaurantManager restaurantManager = RestaurantManager.getInstance();
        ArrayList<RestaurantDAO> restaurants = restaurantManager.getRestaurants(pageNumber, pageSize);
        return mapper.writeValueAsString(DAO_DTO.restaurantDAO_DTO(restaurants));
    }

    @RequestMapping(value = "/v1/restaurants/{restaurantId}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getRestaurant(@PathVariable(value = "restaurantId") String restaurantId) throws IOException, SQLException, RestaurantNotFound {
        RestaurantManager restaurantManager = RestaurantManager.getInstance();
        FoodManager foodManager = FoodManager.getInstance();
        RestaurantDAO restaurant = restaurantManager.getRestaurantById(restaurantId);
        ArrayList<FoodDAO> foods = foodManager.getFoods(restaurantId);
        return mapper.writeValueAsString(DAO_DTO.singleRestaurantDAO_DTO(restaurant, foods));
    }

    @RequestMapping(value = "/v1/foodparty", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public ArrayList<FoodpartyDAO> getFoodParty() throws IOException, SQLException {
        FoodpartyManager foodpartyManager = FoodpartyManager.getInstance();
        return foodpartyManager.getParty();
//        if(loghmeh.getFoodPartyTaskSet() == false){
//            Timer timer = new Timer();
//            timer.schedule(new UpdatePartyRestaurants(), 0, loghmeh.getPARTYFOODUPDATEPERIOD());
//            loghmeh.setFoodPartyTaskSet(true);
//        }
//        try{
//            TimeUnit.SECONDS.sleep(1);
//        }catch (InterruptedException e1){
//            e1.printStackTrace();
//        }
//
//        String[] restaurantFilterParams = {"location", "logo", "estimatedDeliverTime"};
//        FilterProvider filter = new SimpleFilterProvider()
//                .addFilter("restaurant", SimpleBeanPropertyFilter.serializeAllExcept(restaurantFilterParams));
//        ObjectWriter writer = mapper.writer(filter);
//        ArrayList<Restaurant> restaurants = loghmeh.getRestaurantsInParty();
//        if(restaurants.size() == 0)
//            throw new NoRestaurantsAround(new JSONStringCreator().msgCreator("جشن غذایی موجود نمی‌باشد."));
//        String resultStr = writer.writeValueAsString(restaurants);
//        return new JSONStringCreator().partyfoodJson(mapper.readTree(resultStr), loghmeh.calculatePartyRemainingTime());
    }
}
