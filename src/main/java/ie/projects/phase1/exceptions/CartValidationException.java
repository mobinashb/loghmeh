package ie.projects.phase1.exceptions;

public class CartValidationException extends Exception{
    public CartValidationException(String msg){
        super(msg);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
