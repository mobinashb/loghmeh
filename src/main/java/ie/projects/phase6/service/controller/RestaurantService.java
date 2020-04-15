package ie.projects.phase6.service.controller;

import ie.projects.phase6.domain.RestaurantManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class RestaurantService {

    @RequestMapping(value = "/v1/restaurants", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public void getAllRestaurants() throws SQLException {
        RestaurantManager.getInstance();
    }
}
