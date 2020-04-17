package ie.projects.phase6.domain.exceptions;

public class NoRestaurantsAround extends Exception{
    public NoRestaurantsAround(String msg){
        super(msg);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
