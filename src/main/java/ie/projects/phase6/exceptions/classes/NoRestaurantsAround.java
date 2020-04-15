package ie.projects.phase6.exceptions.classes;

public class NoRestaurantsAround extends Exception{
    public NoRestaurantsAround(String msg){
        super(msg);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
