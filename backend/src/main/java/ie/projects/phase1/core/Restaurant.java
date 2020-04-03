package ie.projects.phase1.core;


import com.fasterxml.jackson.annotation.JsonFilter;
import ie.projects.phase1.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@JsonFilter("restaurant")
public class Restaurant
{
    private String id;
    private String name;
    private GeoLocation location;
    private String logo;
    private ArrayList<Food> menu;
    private int estimatedDeliverTime;
    private static final double FINDDELIVERYMANTIME = 60.0;
    private static final double AVGDDELIVERYMANVELOCITY = 5.0;
    private static final double DISTANCERATIO = 2;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) {this.name = name;}

    public GeoLocation getLocation() { return location; }

    public String getLogo() { return logo; }

    public ArrayList<Food> getMenu() { return menu; }

    public int getEstimatedDeliverTime() { return estimatedDeliverTime; }

    public void addFood(Food food){
        this.menu.add(food);
    }

    public Food findFood(String foodName){
        for(Food food : this.menu){
            if(food.getName().equals(foodName))
                return food;
        }
        return null;
    }

    private double calcPopularityAvg(){
        double sum = 0.0;
        for(Food food : menu)
            sum += food.getPopularity();
        return sum / menu.size();
    }

    public double calculateScore(double x0, double y0){
        double distance = getDistance(x0, y0);
        double popularityAverage = calcPopularityAvg();
        return popularityAverage / distance;
    }

    public double getDistance(double x0, double y0){
        return location.distanceCalculator(x0, y0);
    }

    public void deleteSame(){
        ArrayList<Food> foodsMenu = new ArrayList<Food>();
        int i;
        for(i=0; i<menu.size()-1; i++){
            if(!menu.get(i).getName().equals(menu.get(i+1).getName()))
                foodsMenu.add(menu.get(i));
        }
        foodsMenu.add(menu.get(i));
        menu = foodsMenu;
    }

    public double getFoodPrices(HashMap<String, Integer> orders){
        double totalPrice = 0;
        for(Map.Entry element: orders.entrySet())
            totalPrice += findFood((String) element.getKey()).getPrice() * ((int) element.getValue());
        return totalPrice;
    }

    public boolean checkPartyFoodNum(HashMap<String, Integer> orders){
        for (String foodName : orders.keySet()){
            int number = orders.get(foodName);
            int availableNum = findFood(foodName).getCount();
            if(availableNum < number)
                return false;
        }
        return true;
    }

    public void setPartyFoodNum(HashMap<String, Integer> orders){
        for (String foodName : orders.keySet()){
            int number = orders.get(foodName);
            int availableNum = findFood(foodName).getCount();
            findFood(foodName).setCount(availableNum - number);
        }
    }

    public void calculateEstimatedDeliveryTime(GeoLocation userLocation){
        double time = location.distanceCalculator(userLocation.getx(), userLocation.gety()) / AVGDDELIVERYMANVELOCITY;
        time += time / DISTANCERATIO;
        time += FINDDELIVERYMANTIME;
        this.estimatedDeliverTime = (int) time;
    }

    public void convertPartyMenuToMenu(Restaurant restaurant){
        for(Food food : restaurant.getMenu()){
            food.setRestaurantId(restaurant.getId());
            if(findFood(food.getName()) == null){
                Food newFood = Utils.deepCopyFood(food);
                newFood.setPrice(food.getOldPrice());
                newFood.setOldPrice(null);
                newFood.setCount(null);
                addFood(newFood);
            }
        }
    }
}