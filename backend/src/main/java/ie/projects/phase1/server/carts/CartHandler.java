package ie.projects.phase1.server.carts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.projects.phase1.core.Cart;
import ie.projects.phase1.core.Loghmeh;
import ie.projects.phase1.core.User;
import ie.projects.phase1.exceptions.CartValidationException;
import ie.projects.phase1.exceptions.RestaurantNotFound;
import ie.projects.phase1.server.jsonCreator.JSONStringCreator;
import ie.projects.phase1.server.repeatedTasks.CheckOrderStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Timer;

@RestController
public class CartHandler {
    Loghmeh loghmeh = Loghmeh.getInstance();
    ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/v1/cart", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getCart() throws JsonProcessingException {
        User user = loghmeh.getUsers().get(0);
        Cart cart = user.getCart();
        return new JSONStringCreator().cartCreator(cart).toString();
    }

    @RequestMapping(value = "/v1/cart", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String addOrder(@RequestBody CartRequest request) throws CartValidationException, RestaurantNotFound {
        System.out.println(request.getIsParty());
        loghmeh.addToUserCart(loghmeh.getUsers().get(0), request.getFoodName(), request.getNumber(), request.getRestaurantId(), request.getIsParty(), true);
        return "{\"msg\": " + "\"" + "Your order saved successfully" + "\"}";
    }

    @RequestMapping(value = "/v1/cart", method = RequestMethod.PUT, produces = "text/plain;charset=UTF-8")
    public String editOrder(@RequestBody CartRequest request) throws CartValidationException, RestaurantNotFound {
        loghmeh.addToUserCart(loghmeh.getUsers().get(0), request.getFoodName(), request.getNumber(), request.getRestaurantId(), request.getIsParty(), false);
        return "{\"msg\": " + "\"" + "Your order edited successfully" + "\"}";
    }

    @RequestMapping(value = "/v1/cart", method = RequestMethod.DELETE, produces = "text/plain;charset=UTF-8")
    public String deleteOrder(@RequestBody CartRequest request) throws CartValidationException, RestaurantNotFound {
        loghmeh.getUsers().get(0).deleteCart();
        return "{\"msg\": " + "\"" + "Your order deleted successfully" + "\"}";
    }

    @RequestMapping(value = "/v1/cart/finalize", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String finalizeOrder() throws CartValidationException {
        User user = loghmeh.getUsers().get(0);
        user.finalizeOrder();
        Timer timer = new Timer();
        timer.schedule(new CheckOrderStatus(), 0, 3000);
        return "{\"msg\": " + "\"" + "Your cart registered successfully" + "\"}";
    }
}
