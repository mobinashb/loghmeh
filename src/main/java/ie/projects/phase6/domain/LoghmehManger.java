package ie.projects.phase6.domain;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.projects.phase6.domain.core.Restaurant;
import ie.projects.phase6.utilities.HttpRequester;
import ie.projects.phase6.utilities.Utils;

import java.io.IOException;
import java.util.ArrayList;

public class LoghmehManger {
    private static LoghmehManger instance;

    private LoghmehManger(){
    }

    public static LoghmehManger getInstance(){
        if(instance == null)
            instance = new LoghmehManger();
        return instance;
    }

    private Restaurant addRestaurant(String jsonData){
        ObjectMapper mapper = new ObjectMapper();
        try{
            return mapper.readValue(jsonData, Restaurant.class);
        }
        catch (JsonParseException e) { e.printStackTrace();}
        catch (JsonMappingException e) { e.printStackTrace(); }
        catch (IOException e) { e.printStackTrace(); }
        return null;
    }

    public ArrayList<Restaurant> getNewRestaurants(boolean isParty){
        String url;
        if(isParty)
            url = "http://138.197.181.131:8080/foodparty";
        else
            url = "http://138.197.181.131:8080/restaurants";
        String response = new HttpRequester().getRequest(url);
        ArrayList<String> strings = Utils.decodeJsonStrToList(response);
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        for (String restaurantStr : strings)
            restaurants.add(addRestaurant(restaurantStr));
        return restaurants;

    }

}
