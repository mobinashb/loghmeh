package ie.projects.phase1.exceptions;

public class RestaurantNotFound extends Exception{
    public RestaurantNotFound(String msg){
        super(msg);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
