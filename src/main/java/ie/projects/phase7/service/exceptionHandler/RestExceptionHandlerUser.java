package ie.projects.phase7.service.exceptionHandler;

import ie.projects.phase7.domain.exceptions.DuplicateEmail;
import ie.projects.phase7.domain.exceptions.LoginFailure;
import ie.projects.phase7.domain.exceptions.NegativeCreditAmount;
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
