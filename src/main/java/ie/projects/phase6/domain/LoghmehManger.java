package ie.projects.phase6.domain;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.projects.phase6.domain.core.DeliveryMan;
import ie.projects.phase6.domain.core.Restaurant;
import ie.projects.phase6.domain.exceptions.RestaurantNotFound;
import ie.projects.phase6.repository.mapper.Mapper;
import ie.projects.phase6.repository.restaurant.RestaurantDAO;
import ie.projects.phase6.repository.user.UserDAO;
import ie.projects.phase6.utilities.HttpRequester;
import ie.projects.phase6.utilities.Utils;

import java.io.IOException;
import java.sql.SQLException;
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
        System.out.println(jsonData);
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

//        System.out.println(restaurant.getLocationX());
//        System.out.println(restaurant.getLocationY());

        DeliveryMan bestDeliveryMan = null;

        for (String deliverMenStr : strings) {
//            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            ObjectMapper mapper = new ObjectMapper();
            DeliveryMan newDeliveryMan;
            try{
                newDeliveryMan = mapper.readValue(deliverMenStr, DeliveryMan.class);
//                System.out.println(newDeliveryMan.getId());
//                System.out.println(newDeliveryMan.getVelocity());
//                System.out.println(newDeliveryMan.getLocation().getx());
//                System.out.println(newDeliveryMan.getLocation().gety());
//                System.out.println("\n");
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

//        System.out.println("++++++++++++++++++++++++++++++++++++1");
//        System.out.println(bestDeliveryMan.getId());
//        System.out.println(bestDeliveryMan.getVelocity());
//        System.out.println(bestDeliveryMan.getLocation().getx());
//        System.out.println(bestDeliveryMan.getLocation().gety());
//        System.out.println(bestDeliveryMan.getTimeToReach());
//        System.out.println("\n");


        return bestDeliveryMan;
    }
}
