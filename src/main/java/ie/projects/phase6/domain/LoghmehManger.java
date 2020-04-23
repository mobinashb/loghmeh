package ie.projects.phase6.domain;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.projects.phase6.domain.foreignServiceObjects.DeliveryMan;
import ie.projects.phase6.domain.foreignServiceObjects.Restaurant;
import ie.projects.phase6.domain.exceptions.RestaurantNotFound;
import ie.projects.phase6.domain.repeatedTasks.CheckOrderStatus;
import ie.projects.phase6.repository.restaurant.RestaurantDAO;
import ie.projects.phase6.repository.user.UserDAO;
import ie.projects.phase6.utilities.HttpRequester;
import ie.projects.phase6.utilities.Utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;

public class LoghmehManger {
    private static LoghmehManger instance;

    private boolean schedulerSet = false;

    private static final long CHECK_ORDER_STATUS_PERIOD = 3000;

    private LoghmehManger(){
        Timer timer = new Timer();
        timer.schedule(new CheckOrderStatus(), 0, this.CHECK_ORDER_STATUS_PERIOD);
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

    public DeliveryMan selectDeliveryManForOrder(String userId, String restaurantId) throws SQLException, RestaurantNotFound{
        String response = new HttpRequester().getRequest("http://138.197.181.131:8080/deliveries");
        ArrayList<String> strings = Utils.decodeJsonStrToList(response);
        if(strings.size() == 0)
            return null;

        UserDAO user = UserManager.getInstance().getUserById(userId);
        RestaurantDAO restaurant = RestaurantManager.getInstance().getRestaurantById(restaurantId);

        DeliveryMan bestDeliveryMan = null;

        for (String deliverMenStr : strings) {
            ObjectMapper mapper = new ObjectMapper();
            DeliveryMan newDeliveryMan;
            try{
                newDeliveryMan = mapper.readValue(deliverMenStr, DeliveryMan.class);
                double bestScore = Double.POSITIVE_INFINITY;
                double restaurantToUserDistance = Math.sqrt( Math.pow(user.getLocationX()-restaurant.getLocationX(), 2) +
                        Math.pow(user.getLocationY()-restaurant.getLocationY(), 2));

                double deliveryManToRestaurantDistance = Math.sqrt( Math.pow(restaurant.getLocationX()-newDeliveryMan.getLocation().getx(), 2) +
                        Math.pow(restaurant.getLocationY()-newDeliveryMan.getLocation().gety(), 2));

                if((restaurantToUserDistance+deliveryManToRestaurantDistance) / newDeliveryMan.getVelocity() < bestScore){
                    bestDeliveryMan = newDeliveryMan;
                    bestScore = (restaurantToUserDistance+deliveryManToRestaurantDistance) / newDeliveryMan.getVelocity();
                    bestDeliveryMan.setTimeToReach(bestScore);
                }

            }
            catch (JsonParseException e) { e.printStackTrace();}
            catch (JsonMappingException e) { e.printStackTrace(); }
            catch (IOException e) { e.printStackTrace(); }
        }


        return bestDeliveryMan;
    }
}
