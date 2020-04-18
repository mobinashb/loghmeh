package ie.projects.phase6.domain;

import ie.projects.phase6.domain.repeatedTasks.UpdateFoodParty;
import ie.projects.phase6.repository.food.FoodDAO;
import ie.projects.phase6.repository.foodparty.FoodpartyRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;

public class FoodpartyManager {
    private static FoodpartyManager instance;
    private static final long FOODPARTY_UPDATE_PERIOD = 18000000;

    private FoodpartyRepository foodpartyRepository;
    private long lastFoodpartyUpdateTime = 0;
    private boolean schedulerIsSet = false;

    private FoodpartyManager() throws SQLException{
        this.foodpartyRepository = FoodpartyRepository.getInstance();
    }

    public static FoodpartyManager getInstance() throws SQLException {
        if (instance == null)
            instance = new FoodpartyManager();
        return instance;
    }

    public boolean setupScheduler(){
        if(!this.schedulerIsSet){
            Timer timer = new Timer();
            timer.schedule(new UpdateFoodParty(), 0, FOODPARTY_UPDATE_PERIOD);
            this.schedulerIsSet = true;
            return true;
        }
        return false;
    }

    public double getFoodpartyRemainingTime(){
        return (FOODPARTY_UPDATE_PERIOD - (System.currentTimeMillis() - this.lastFoodpartyUpdateTime)) / 1000;
    }

    public void setLastFoodpartyUpdateTime(long lastPartFoodUpdateTime) {
        this.lastFoodpartyUpdateTime = lastPartFoodUpdateTime;
    }

    public void deleteTable() throws SQLException{
        this.foodpartyRepository.deleteTable();
    }

    public void createTable() throws SQLException{
        this.foodpartyRepository.createTable();
    }

    public ArrayList<FoodDAO> getParty() throws SQLException{
        return this.foodpartyRepository.getParty();
    }

    public FoodDAO findFoodpartyById(String restaurantId, String foodName) throws SQLException{
        try {
            return this.foodpartyRepository.findFoodpartyById(restaurantId, foodName);
        }
        catch (SQLException e1){
            return null;
        }
    }
}
