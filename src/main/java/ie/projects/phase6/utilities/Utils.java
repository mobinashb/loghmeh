package ie.projects.phase6.utilities;

import ie.projects.phase6.domain.core.DeliveryMan;
import ie.projects.phase6.domain.core.Food;
import ie.projects.phase6.domain.core.GeoLocation;
import ie.projects.phase6.domain.core.Restaurant;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Utils {
    public static ArrayList<String> decodeJsonStrToList(String jsonStr){
        ArrayList<String> restaurantsString = new ArrayList<String>();
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(jsonStr);
            JSONArray array = (JSONArray) obj;
            for(int i=0; i<array.size(); i++)
                restaurantsString.add(array.get(i).toString());
        }
        catch(ParseException e) {e.printStackTrace(); }
        return restaurantsString;
    }

    public static DeliveryMan selectBestDeliveryMan(Restaurant restaurant, ArrayList<DeliveryMan> deliveryMen, float X0, float Y0){
        GeoLocation restaurantLocation = restaurant.getLocation();
        double restaurantToUserDistance = restaurant.getDistance(X0, Y0);
        Collections.sort(deliveryMen, Comparator.comparing((DeliveryMan deliveryMan) -> deliveryMan.calcReceiveToUserTime(restaurantLocation, restaurantToUserDistance)));
        return deliveryMen.get(0);
    }

    public static Food deepCopyFood(Food food){
        Food newFood = new Food();
        newFood.setName(food.getName());
        newFood.setDescription(food.getDescription());
        newFood.setImage(food.getImage());
        newFood.setPopularity(food.getPopularity());
        newFood.setRestaurantId(food.getRestaurantId());
        newFood.setPrice(food.getPrice());
        return newFood;
    }

}
