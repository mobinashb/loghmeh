package ie.projects.phase1.services.carts;

import ie.projects.phase1.exceptions.DifferentRestaurantsForCart;
import ie.projects.phase1.exceptions.FoodNotInRestaurant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandlerCart {

    @ExceptionHandler(FoodNotInRestaurant.class)
    public ResponseEntity<Object> foodNotInRestaurant(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DifferentRestaurantsForCart.class)
    public ResponseEntity<Object> differentRestaurantsForCart(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
