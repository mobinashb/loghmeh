package ie.projects.phase6.service.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.projects.phase6.domain.CartManager;
import ie.projects.phase6.domain.OrderManager;
import ie.projects.phase6.domain.UserManager;
import ie.projects.phase6.domain.exceptions.CartNotFound;
import ie.projects.phase6.domain.exceptions.CartValidationException;
import ie.projects.phase6.domain.exceptions.FoodPartyExpiration;
import ie.projects.phase6.domain.exceptions.RestaurantNotFound;
import ie.projects.phase6.repository.cart.CartDAO;
import ie.projects.phase6.repository.finalizedCart.FinalizedCartDAO;
import ie.projects.phase6.repository.finalizedCart.FinalizedCartRepository;
import ie.projects.phase6.repository.order.OrderDAO;
import ie.projects.phase6.utilities.DAO_DTO;
import ie.projects.phase6.utilities.JsonStringCreator;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/v1/cart", method = RequestMethod.DELETE, produces = "text/plain;charset=UTF-8")
    public String deleteOrder(@RequestBody CartRequest request) throws CartValidationException, SQLException {
        UserManager.getInstance().deleteCart("123456789123");
        return JsonStringCreator.msgCreator("سبد خرید شما با موفقیت حذف شد");
    }

    @RequestMapping(value = "/v1/orders", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getAllCarts() throws SQLException, IOException {
        ArrayList<FinalizedCartDAO> carts = UserManager.getInstance().getAllOrders("123456789123");
        return mapper.writeValueAsString(DAO_DTO.finalizedCartsDAO_DTO(carts));
    }

    @RequestMapping(value = "/v1/orders/{cartId}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getCart(@PathVariable(value = "cartId") String cartId) throws CartNotFound, SQLException {
        ArrayList<OrderDAO> orders = OrderManager.getInstance().getOrdersOfCart(Integer.parseInt(cartId));
        FinalizedCartDAO cart = FinalizedCartRepository.getInstance().getCart(Integer.parseInt(cartId));
        if(cart == null)
            throw new CartNotFound("سبد خریدی با شناسه مورد نظر یافت نشد.");

        return JsonStringCreator.getCartJson(cart, orders);
    }
}
