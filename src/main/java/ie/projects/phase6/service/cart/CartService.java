package ie.projects.phase6.service.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.projects.phase6.domain.UserManager;
import ie.projects.phase6.domain.exceptions.CartValidationException;
import ie.projects.phase6.domain.exceptions.FoodPartyExpiration;
import ie.projects.phase6.domain.exceptions.RestaurantNotFound;
import ie.projects.phase6.repository.cart.CartDAO;
import ie.projects.phase6.repository.order.OrderDAO;
import ie.projects.phase6.utilities.DAO_DTO;
import ie.projects.phase6.utilities.JsonStringCreator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class CartService {
    ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/v1/cart", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getCart() throws SQLException, IOException {
        Object[] cart  = UserManager.getInstance().getCart("123456789123");
        if(((CartDAO) cart[0] == null) && ((ArrayList< OrderDAO>) cart[1] == null))
            return JsonStringCreator.msgCreator("سبد خرید شما خالی می‌باشد");
        return mapper.writeValueAsString(DAO_DTO.cartDAO_DTO((CartDAO) cart[0], (ArrayList< OrderDAO>) cart[1]));
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

    @RequestMapping(value = "/v1/cart/finalize", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String finalizeOrder() throws CartValidationException, FoodPartyExpiration, SQLException {
        UserManager.getInstance().finalizeOrder("123456789123");
        return JsonStringCreator.msgCreator("سفارش شما ثبت نهایی گردید");
    }
//
//    @RequestMapping(value = "/v1/cart", method = RequestMethod.DELETE, produces = "text/plain;charset=UTF-8")
//    public String deleteOrder(@RequestBody CartRequest request) throws CartValidationException, RestaurantNotFound {
//        loghmeh.getUsers().get(0).deleteCart();
//        return new JSONStringCreator().msgCreator("سبد خرید شما با موفقیت حذف شد.");
//    }

}
