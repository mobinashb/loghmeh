package ie.projects.phase1.core;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.projects.phase1.Utils;
import ie.projects.phase1.exceptions.CartValidationException;
import ie.projects.phase1.exceptions.RestaurantNotFound;
import ie.projects.phase1.requestSender.HttpRequester;
import ie.projects.phase1.server.jsonCreator.JSONStringCreator;


import java.io.IOException;
import java.util.ArrayList;

public class Loghmeh {

    private static final double X0 = 0.0;
    private static final double Y0 = 0.0;
    private static final double DISTANCEOFRESTAURANTSTOSHOW = 170;
    private static final int PARTYFOODUPDATEPERIOD = 1800000;

    private static Loghmeh instance;


    private ArrayList<Restaurant> restaurants;
    private ArrayList<DeliveryMan> deliveryMen;
    private ArrayList<Restaurant> restaurantsInParty;
    private ArrayList<User> users;

    private long lastPartFoodUpdateTime;

    public ArrayList<User> getUsers() { return users; }

    private Loghmeh(){
        restaurants = new ArrayList<>();
        deliveryMen = new ArrayList<>();
        restaurantsInParty = new ArrayList<>();
        users= new ArrayList<>();
        User testUser = new User("1", "احسان", "خامس‌ پناه", "09124820194", "ekhamespanah@yahoo.com", 100000.0);
        users.add(testUser);
        lastPartFoodUpdateTime = 0;
        addAllToLoghmeh("http://138.197.181.131:8080/restaurants", "restaurant");
    }

    public double calculatePartyRemainingTime(){
        return (PARTYFOODUPDATEPERIOD - (System.currentTimeMillis() - lastPartFoodUpdateTime))/1000;
    }

    public void setLastPartFoodUpdateTime(long lastPartFoodUpdateTime) {
        this.lastPartFoodUpdateTime = lastPartFoodUpdateTime;
    }

    public static int getPARTYFOODUPDATEPERIOD() {
        return PARTYFOODUPDATEPERIOD;
    }

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
                Restaurant restaurant1 = new Restaurant(restaurant.getId(), restaurant.getName(), restaurant.getLocation(), restaurant.getLogo(), restaurant.getMenu());
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

    public void addToUserCart(User user, String foodName, int number, String restaurantId, boolean isParty, boolean isNew) throws CartValidationException, RestaurantNotFound {
        Restaurant restaurant;
        if(isParty == true)
            restaurant = findRestaurantInPartyById(restaurantId);
        else
            restaurant = findRestaurantById(restaurantId);
        if(restaurant == null)
            throw new RestaurantNotFound(new JSONStringCreator().msgCreator("رستورانی با شناسه درخواست‌شده موجود نمی‌باشد."));

        if(restaurant.containFood(foodName)) {
            if(isParty){
                if(restaurant.findFood(foodName).getCount() < number)
                    throw new CartValidationException(new JSONStringCreator().msgCreator("غذا از جشن غذا برداشته شده‌است."));
            }
            user.addToCart(foodName, number, restaurantId, isParty, isNew);
            return;
        }
        throw new CartValidationException(new JSONStringCreator().msgCreator("رستوران مدنظر، شامل غذای درخواست‌شده نمی‌باشد"));
    }

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

    public DeliveryMan selectBestDeliveryMan(Restaurant restaurant, GeoLocation userLocation){
        return Utils.selectBestDeliveryMan(restaurant, deliveryMen, userLocation.getx(), userLocation.gety());
    }

    public ArrayList<Restaurant> getRestaurantsInParty() { return restaurantsInParty; }

}