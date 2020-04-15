package ie.projects.phase6.exceptions.handler;

import ie.projects.phase6.exceptions.classes.NoRestaurantsAround;
import ie.projects.phase6.exceptions.classes.RestaurantNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandlerRestaurant extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoRestaurantsAround.class)
    public ResponseEntity<Object> noRestaurant(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(RestaurantNotFound.class)
    public ResponseEntity<Object> restaurantNotFound(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
