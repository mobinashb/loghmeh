package ie.projects.phase6.exceptions.classes;

public class NegativeCreditAmount extends Exception {
    public NegativeCreditAmount(String msg){
        super(msg);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
