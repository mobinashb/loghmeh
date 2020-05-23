package loghmeh.utilities;

import loghmeh.domain.foreignServiceObjects.Food;
import loghmeh.domain.foreignServiceObjects.Restaurant;
import loghmeh.repository.food.FoodDAO;
import loghmeh.repository.restaurant.RestaurantDAO;

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
