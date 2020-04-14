package ie.projects.phase6.service.controller;

import ie.projects.phase1.exceptions.NoRestaurantsAround;
import ie.projects.phase6.domain.RestaurantManager;
import ie.projects.phase6.repository.dao.RestaurantDAO;
import ie.projects.phase6.service.dto.RestaurantDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class RestaurantService {
    @RequestMapping(value = "/v1/restaurants", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public ArrayList<RestaurantDTO> getAllRestaurants() throws IOException, NoRestaurantsAround {
        ArrayList<RestaurantDAO> restaurants = RestaurantManager.getInstance().getRestaurants();
        return new ArrayList<>();
    }
}
