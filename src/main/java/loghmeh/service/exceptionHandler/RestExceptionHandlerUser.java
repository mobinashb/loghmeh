package loghmeh.service.exceptionHandler;

import loghmeh.domain.exceptions.DuplicateEmail;
import loghmeh.domain.exceptions.LoginFailure;
import loghmeh.domain.exceptions.NegativeCreditAmount;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandlerUser extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NegativeCreditAmount.class)
    public ResponseEntity<Object> negativeAmount(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateEmail.class)
    public ResponseEntity<Object> duplicateEmail(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(LoginFailure.class)
    public ResponseEntity<Object> loginFailure(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
