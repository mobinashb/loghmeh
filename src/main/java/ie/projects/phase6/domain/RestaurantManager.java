package ie.projects.phase6.domain;

import ie.projects.phase6.repository.dao.RestaurantDAO;

import java.util.ArrayList;

public class RestaurantManager {
    private static RestaurantManager instance;
    private RestaurantManager(){

    }
    public static RestaurantManager getInstance(){
        if (instance == null)
            instance = new RestaurantManager();
        return instance;
    }

//    public ArrayList<RestaurantDAO> getRestaurants(){
//
//    }
}
