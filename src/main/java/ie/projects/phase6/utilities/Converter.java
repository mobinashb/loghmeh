package ie.projects.phase6.utilities;

import ie.projects.phase6.domain.foreignServiceObjects.Food;
import ie.projects.phase6.domain.foreignServiceObjects.Restaurant;
import ie.projects.phase6.repository.food.FoodDAO;
import ie.projects.phase6.repository.restaurant.RestaurantDAO;

import java.util.ArrayList;

public class Converter {
    public static ArrayList<RestaurantDAO> convertToRestaurantDAO(ArrayList<Restaurant> restaurants){
        ArrayList<RestaurantDAO> result = new ArrayList<>();
        for (Restaurant restaurant: restaurants){
            result.add(new RestaurantDAO(restaurant.getId(), restaurant.getName(), restaurant.getLogo(), restaurant.getLocation().getx(), restaurant.getLocation().gety()));
        }
        return result;
    }

    public static ArrayList<FoodDAO> convertToFoodDAO(ArrayList<Restaurant> restaurants, boolean isParty){
        ArrayList<FoodDAO> result = new ArrayList<>();
        for (Restaurant restaurant: restaurants){
            for (Food food: restaurant.getMenu()) {
                float price = (isParty) ? food.getOldPrice() : food.getPrice();
                result.add(new FoodDAO(restaurant.getId(), food.getName(), food.getDescription(), food.getPopularity(), food.getImage(), price));
            }
        }
        return result;
    }

    public static ArrayList<FoodDAO> convertToFoodpartyDAO(ArrayList<Restaurant> restaurants){
        ArrayList<FoodDAO> result = new ArrayList<>();
        for (Restaurant restaurant: restaurants){
            for (Food food: restaurant.getMenu())
                result.add(new FoodDAO(restaurant.getId(), food.getName(), food.getCount(), food.getPrice()));
        }
        return result;
    }
}
