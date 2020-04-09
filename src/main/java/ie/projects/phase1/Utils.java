package ie.projects.phase1;

import ie.projects.phase1.core.DeliveryMan;
import ie.projects.phase1.core.Food;
import ie.projects.phase1.core.GeoLocation;
import ie.projects.phase1.core.Restaurant;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
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

    public static ArrayList<Restaurant> top3InList(ArrayList<Restaurant> restaurants, double X0, double Y0){
        Collections.sort(restaurants, Comparator.comparing((Restaurant restaurant) -> restaurant.calculateScore(X0, Y0)));
        return new ArrayList<Restaurant>(restaurants.subList(0, 3));
    }

    public static DeliveryMan selectBestDeliveryMan(Restaurant restaurant, ArrayList<DeliveryMan> deliveryMen, double X0, double Y0){
        GeoLocation restaurantLocation = restaurant.getLocation();
        double restaurantToUserDistance = restaurant.getDistance(X0, Y0);
        Collections.sort(deliveryMen, Comparator.comparing((DeliveryMan deliveryMan) -> deliveryMan.calcReceiveToUserTime(restaurantLocation, restaurantToUserDistance)));
        return deliveryMen.get(0);
    }

    public static String intToTime(int seconds)
    {
        String str = "";
        int hours = 0;
        int minutes = 0;
        while (seconds >= 3600) {
            hours += 1;
            seconds -= 3600;
        }
        while (seconds >= 60) {
            minutes += 1;
            seconds -= 60;
        }
        if (hours > 0)
            str += hours + " hour(s) ";
        if (minutes > 0)
            str += minutes + " minute(s) ";
        if (seconds > 0)
            str += seconds + " second(s) ";
        return str;
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
