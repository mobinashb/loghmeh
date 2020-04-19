package ie.projects.phase6.service.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.projects.phase6.domain.UserManager;
import ie.projects.phase6.domain.exceptions.CartValidationException;
import ie.projects.phase6.domain.exceptions.FoodPartyExpiration;
import ie.projects.phase6.domain.exceptions.RestaurantNotFound;
import ie.projects.phase6.repository.cart.CartDAO;
import ie.projects.phase6.utilities.JsonStringCreator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class CartService {
    ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/v1/cart", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getCart() throws SQLException {
        CartDAO cart = UserManager.getInstance().getCart("123456789123");
        return null;
//        return JsonStringCreator.cartCreator(cart).toString();
    }

    @RequestMapping(value = "/v1/cart", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String addOrder(@RequestBody CartRequest request) throws SQLException, CartValidationException, RestaurantNotFound, FoodPartyExpiration {
        UserManager.getInstance().addToCart("123456789123", request.getFoodName(), request.getNumber(), request.getRestaurantId(), request.getIsParty(), true);
        return JsonStringCreator.msgCreator( "سفارش شما با موفقیت ثبت شد.");
    }

    @RequestMapping(value = "/v1/cart", method = RequestMethod.PUT, produces = "text/plain;charset=UTF-8")
    public String editOrder(@RequestBody CartRequest request) throws SQLException, CartValidationException, RestaurantNotFound, FoodPartyExpiration {
        UserManager.getInstance().addToCart("123456789123", request.getFoodName(), request.getNumber(), request.getRestaurantId(), request.getIsParty(), false);
        return JsonStringCreator.msgCreator( "سفارش شما با موفقیت ثبت شد.");
    }
//
//    @RequestMapping(value = "/v1/cart", method = RequestMethod.DELETE, produces = "text/plain;charset=UTF-8")
//    public String deleteOrder(@RequestBody CartRequest request) throws CartValidationException, RestaurantNotFound {
//        loghmeh.getUsers().get(0).deleteCart();
//        return new JSONStringCreator().msgCreator("سبد خرید شما با موفقیت حذف شد.");
//    }

}
