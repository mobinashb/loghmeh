package ie.projects.phase6.exceptions.classes;

public class RestaurantNotFound extends Exception{
    public RestaurantNotFound(String msg){
        super(msg);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
