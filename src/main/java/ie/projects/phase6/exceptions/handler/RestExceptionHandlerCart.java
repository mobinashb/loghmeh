package ie.projects.phase6.exceptions.handler;

import ie.projects.phase6.exceptions.classes.CartNotFound;
import ie.projects.phase6.exceptions.classes.CartValidationException;
import ie.projects.phase6.exceptions.classes.FoodPartyExpiration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandlerCart {

    @ExceptionHandler(CartValidationException.class)
    public ResponseEntity<Object> foodNotInRestaurant(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CartNotFound.class)
    public ResponseEntity<Object> cartNotFound(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FoodPartyExpiration.class)
    public ResponseEntity<Object> foodpartyExpired(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
