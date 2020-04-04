package ie.projects.phase1.exceptions;

public class DifferentRestaurantsForCart extends Exception{
    public DifferentRestaurantsForCart(String msg){
        super(msg);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
