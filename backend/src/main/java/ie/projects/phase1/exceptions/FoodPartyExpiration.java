package ie.projects.phase1.exceptions;

public class FoodPartyExpiration extends Exception{
    public FoodPartyExpiration(String msg){
        super(msg);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
