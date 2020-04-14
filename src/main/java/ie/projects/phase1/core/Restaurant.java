package ie.projects.phase1.core;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import ie.projects.phase1.utils.Utils;

import java.util.ArrayList;

@JsonFilter("restaurant")
public class Restaurant
{
    private String id;
    private String name;
    private GeoLocation location;
    private String logo;
    private ArrayList<Food> menu;

    @JsonCreator
    public Restaurant(@JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("location") GeoLocation location, @JsonProperty("logo") String logo, @JsonProperty("menu")ArrayList<Food> menu){
        this.id = id;
        this.name = name;
        this.location = location;
        this.logo = logo;
        this.menu = menu;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) {this.name = name;}

    public GeoLocation getLocation() { return location; }

    public String getLogo() { return logo; }

    public ArrayList<Food> getMenu() { return menu; }

    public void addFood(Food food){
        for(Food menuFoods: this.menu){
            if(menuFoods.getName().equals(food.getName()))
                return;
        }
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

    public boolean containFood(String foodName){
        for(Food food: this.getMenu()){
            if(food.getName().equals(foodName))
                return true;
        }
        return false;
    }

    public boolean checkPartyFoodNum(ArrayList<Order> orders){
        for (Order order : orders){
            int number = order.getFoodNum();
            int availableNum = findFood(order.getFoodName()).getCount();
            if(availableNum < number)
                return false;
        }
        return true;
    }

    public void setPartyFoodNum(ArrayList<Order> orders){
        for (Order order : orders){
            int number = order.getFoodNum();
            int availableNum = findFood(order.getFoodName()).getCount();
            findFood(order.getFoodName()).setCount(availableNum - number);
        }
    }

    public void convertPartyMenuToMenu(Restaurant restaurant){
        for(Food food : restaurant.getMenu()){
            food.setRestaurantId(restaurant.getId());
            Food newFood = Utils.deepCopyFood(food);
            newFood.setPrice(food.getOldPrice());
            newFood.setOldPrice(null);
            newFood.setCount(null);
            addFood(newFood);
        }
    }
}