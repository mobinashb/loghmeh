package ie.projects.phase1.server.carts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import ie.projects.phase1.core.Cart;
import ie.projects.phase1.core.Loghmeh;
import ie.projects.phase1.core.User;
import ie.projects.phase1.exceptions.CartNotFound;
import ie.projects.phase1.server.jsonCreator.JSONStringCreator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RegisteredCartsHandler {
    Loghmeh loghmeh = Loghmeh.getInstance();
    ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/v1/orders", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getAllCarts() throws IOException {
        User user = loghmeh.getUsers().get(0);
        String[] userFilterParams = {"orders", "partyOrders", "deliveryManId", "deliveryManFoundedTime", "deliveryManTimeToReach", "remainingTimeToDeliver"};
        FilterProvider filter = new SimpleFilterProvider().addFilter("user", SimpleBeanPropertyFilter.serializeAllExcept(userFilterParams));
        ObjectWriter writer = mapper.writer(filter);
        return new JSONStringCreator().ordersCreator(user.getAllOrders());
    }

    @RequestMapping(value = "/v1/orders/{cartId}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getCart(@PathVariable(value = "cartId") String cartId) throws CartNotFound{
        System.out.println(cartId);
        User user = loghmeh.getUsers().get(0);
        Cart cart = user.findCartById(cartId);
        if(cart == null)
            throw new CartNotFound("سبد خریدی با شناسه مورد نظر یافت نشد.");
        return new JSONStringCreator().cartCreator(cart).toString();
    }
}
