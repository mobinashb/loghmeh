package ie.projects.phase6.exceptions.classes;

public class FoodPartyExpiration extends Exception{
    public FoodPartyExpiration(String msg){
        super(msg);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
