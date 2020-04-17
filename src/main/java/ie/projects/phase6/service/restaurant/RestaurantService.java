package ie.projects.phase6.service.restaurant;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.projects.phase6.domain.FoodManager;
import ie.projects.phase6.domain.FoodpartyManager;
import ie.projects.phase6.domain.RestaurantManager;
import ie.projects.phase6.domain.exceptions.RestaurantNotFound;
import ie.projects.phase6.repository.dao.FoodDAO;
import ie.projects.phase6.repository.dao.RestaurantDAO;
import ie.projects.phase6.utilities.DAO_DTO;
import ie.projects.phase6.utilities.JsonStringCreator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
    public String getFoodParty() throws IOException, SQLException {
        FoodpartyManager foodpartyManager = FoodpartyManager.getInstance();
        if(foodpartyManager.setupScheduler()){
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e1){
                e1.printStackTrace();
            }
        }
        double remainingTime = foodpartyManager.getFoodpartyRemainingTime();
        ArrayList<FoodDAO> foods = foodpartyManager.getParty();
        String foodpartyStr = mapper.writeValueAsString(DAO_DTO.foodpartyDAO_DTO(foods));
        return JsonStringCreator.foodpartyJson(mapper.readTree(foodpartyStr), remainingTime);
    }
}