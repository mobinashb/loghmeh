package ie.projects.phase1.exceptions;

public class FoodNotInRestaurant extends Exception{
    public FoodNotInRestaurant(String msg){
        super(msg);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
