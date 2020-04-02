package ie.projects.phase1.core;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.projects.phase1.Utils;
import ie.projects.phase1.requestSender.HttpRequester;


import java.io.IOException;
import java.util.ArrayList;

public class Loghmeh {

    private static final double X0 = 0.0;
    private static final double Y0 = 0.0;
    private static final double DISTANCEOFRESTAURANTSTOSHOW = 170;

    private static Loghmeh instance;

    private long lastFoodPartyReceiveTime;

    private ArrayList<Restaurant> restaurants;
    private ArrayList<DeliveryMan> deliveryMen;
    private ArrayList<Restaurant> restaurantsInParty;
    private ArrayList<User> users;

    public ArrayList<User> getUsers() { return users; }

    private Loghmeh(){
        restaurants = new ArrayList<Restaurant>();
        deliveryMen = new ArrayList<DeliveryMan>();
        restaurantsInParty = new ArrayList<Restaurant>();
        lastFoodPartyReceiveTime = 0;
        users= new ArrayList<User>();
        User testUser = new User("1", "Ehsan", "khamespanah", "09124820194", "ekhamespanah@yahoo.com", 100000.0);
        users.add(testUser);
        addAllToLoghmeh("http://138.197.181.131:8080/restaurants", "restaurant");
        addAllToLoghmeh("http://138.197.181.131:8080/foodparty", "party");
    }

    public long getLastFoodPartyReceiveTime() { return lastFoodPartyReceiveTime; }
    public void setLastFoodPartyReceiveTime(long lastFoodPartyReceiveTime) { this.lastFoodPartyReceiveTime = lastFoodPartyReceiveTime; }

    public static Loghmeh getInstance(){
        if(instance == null)
            instance = new Loghmeh();
        return instance;
    }

    public Restaurant findRestaurantById(String id){
        for(Restaurant restaurant: this.restaurants){
            if(restaurant.getId().equals(id))
                return restaurant;
        }
        return null;
    }

    public DeliveryMan findDeliveryManById(String id){
        for(DeliveryMan deliveryMan: this.deliveryMen){
            if(deliveryMan.getId().equals(id))
                return deliveryMan;
        }
        return null;
    }

    public Restaurant findRestaurantInPartyById(String id){
        for(Restaurant restaurant: this.restaurantsInParty){
            if(restaurant.getId().equals(id))
                return restaurant;
        }
        return null;
    }

    public ArrayList<Restaurant> getRestaurants() { return restaurants; }

    public ArrayList<Restaurant> getRestaurantsInArea()
    {
        ArrayList<Restaurant> restaurantsInArea = new ArrayList<>();
        for (Restaurant res : this.restaurants) {
            if (res.getDistance(X0, Y0) <= this.DISTANCEOFRESTAURANTSTOSHOW)
                restaurantsInArea.add(res);
        }
        return restaurantsInArea;
    }

    public void addRestaurant(String jsonData){
        ObjectMapper mapper = new ObjectMapper();
        try{
            Restaurant restaurant = mapper.readValue(jsonData, Restaurant.class);
            restaurant.calculateEstimatedDeliveryTime(new GeoLocation(X0, Y0));
            if(findRestaurantById(restaurant.getId()) == null) {
                restaurants.add(restaurant);
                restaurant.deleteSame();
            }
        }
        catch (JsonParseException e) { e.printStackTrace();}
        catch (JsonMappingException e) { e.printStackTrace(); }
        catch (IOException e) { e.printStackTrace(); }
    }

    public void addDeliveryMen(String jsonData){
        ObjectMapper mapper = new ObjectMapper();
        try{
            DeliveryMan deliveryMan = mapper.readValue(jsonData, DeliveryMan.class);
            if(findDeliveryManById(deliveryMan.getId()) == null) {
                deliveryMen.add(deliveryMan);
                System.out.println("deliveryMan with the id " + deliveryMan.getId() + " has been successfully added");
            }
        }
        catch (JsonParseException e) { e.printStackTrace();}
        catch (JsonMappingException e) { e.printStackTrace(); }
        catch (IOException e) { e.printStackTrace(); }
    }

    public void addToParty(String jsonData){
        ObjectMapper mapper = new ObjectMapper();
        try{
            Restaurant restaurant = mapper.readValue(jsonData, Restaurant.class);
            Restaurant foundedRestaurant = findRestaurantById(restaurant.getId());
            if(foundedRestaurant == null) {
                Restaurant restaurant1 = restaurant;
                restaurant1.convertPartyMenuToMenu(restaurant);
                this.restaurants.add(restaurant1);
            }
            else
                foundedRestaurant.convertPartyMenuToMenu(restaurant);


            if(findRestaurantInPartyById(restaurant.getId()) == null)
                this.restaurantsInParty.add(restaurant);
        }
        catch (JsonParseException e) { e.printStackTrace();}
        catch (JsonMappingException e) { e.printStackTrace(); }
        catch (IOException e) { e.printStackTrace(); }
    }

    private boolean validateOrder(Restaurant restaurant, String foodName){
        if(restaurant == null)
            return false;
        else if(restaurant.findFood(foodName) == null)
            return false;
        return true;
    }

    public boolean addToCart(String foodName, String restaurantId){
        Restaurant restaurant = findRestaurantInPartyById(restaurantId);

        if(validateOrder(restaurant, foodName)) {
            if(restaurant.findFood(foodName).getCount() <= 0){
                System.out.println("This food isn't in party anymore.");
                return false;
            }
            return users.get(0).addToCart(foodName, restaurantId, true);
        }

        else {
            restaurant = findRestaurantById(restaurantId);
            if (restaurant == null) return false;
            else if(validateOrder(restaurant, foodName)) {
                return users.get(0).addToCart(foodName, restaurantId, false);
            }
            else System.out.println("Can't add to cart. Check your request.");
        }

        return false;
    }


    public Cart getCart() { return users.get(0).getCart(); }

    public boolean addAllToLoghmeh(String url, String inputType){
        String response = new HttpRequester().getRequest(url);
        ArrayList<String> strings = Utils.decodeJsonStrToList(response);
        if(inputType.equals("restaurant")) {
            restaurants.clear();
            for (String restaurantStr : strings)
                addRestaurant(restaurantStr);
        }
        else if(inputType.equals("deliveryMan")){
            deliveryMen.clear();
            if(strings.size() == 0)
                return false;
            for (String deliverMenStr : strings)
                addDeliveryMen(deliverMenStr);
        }
        else if(inputType.equals("party")){
            restaurantsInParty.clear();
            for (String party : strings)
                addToParty(party);
        }
        return true;
    }

    public String finalizeOrder(){
        return users.get(0).finalizeOrder();
    }

    public DeliveryMan selectBestDeliveryMan(Restaurant restaurant, GeoLocation userLocation){
        return Utils.selectBestDeliveryMan(restaurant, deliveryMen, userLocation.getx(), userLocation.gety());
    }

    public boolean isInArea(String restaurantId) {
        return getRestaurantsInArea().contains(findRestaurantById(restaurantId));
    }

    public ArrayList<Restaurant> getRestaurantsInParty() { return restaurantsInParty; }

}