package loghmeh.domain.exceptions;

public class NegativeCreditAmount extends Exception {
    public NegativeCreditAmount(String msg){
        super(msg);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
