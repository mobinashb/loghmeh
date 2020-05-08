package ie.projects.phase7.service.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.projects.phase7.domain.OrderManager;
import ie.projects.phase7.domain.UserManager;
import ie.projects.phase7.domain.exceptions.CartNotFound;
import ie.projects.phase7.domain.exceptions.CartValidationException;
import ie.projects.phase7.domain.exceptions.FoodPartyExpiration;
import ie.projects.phase7.domain.exceptions.RestaurantNotFound;
import ie.projects.phase7.repository.cart.CartDAO;
import ie.projects.phase7.repository.finalizedCart.FinalizedCartDAO;
import ie.projects.phase7.repository.finalizedCart.FinalizedCartRepository;
import ie.projects.phase7.repository.order.OrderDAO;
import ie.projects.phase7.service.cart.request.CartRequest;
import ie.projects.phase7.utilities.ConvertDAOToDTO;
import ie.projects.phase7.utilities.JsonStringCreator;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class CartService {
    ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/v1/cart", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getCart(@RequestAttribute("id") String userId) throws SQLException, IOException {
        Object[] cart  = UserManager.getInstance().getCart(userId);
        if(((CartDAO) cart[0] == null) && ((ArrayList< OrderDAO>) cart[1] == null))
            return JsonStringCreator.msgCreator("سبد خرید شما خالی می‌باشد");
        return mapper.writeValueAsString(ConvertDAOToDTO.cartDAO_DTO((CartDAO) cart[0], (ArrayList< OrderDAO>) cart[1]));
    }

    @RequestMapping(value = "/v1/cart", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String addOrder(@RequestBody CartRequest request, @RequestAttribute("id") String userId) throws SQLException, CartValidationException, RestaurantNotFound, FoodPartyExpiration {
        UserManager.getInstance().addToCart(userId, request.getFoodName(), request.getNumber(), request.getRestaurantId(), request.getIsParty(), true);
        return JsonStringCreator.msgCreator( "سفارش شما با موفقیت ثبت شد.");
    }

    @RequestMapping(value = "/v1/cart", method = RequestMethod.PUT, produces = "text/plain;charset=UTF-8")
    public String editOrder(@RequestBody CartRequest request, @RequestAttribute("id") String userId) throws SQLException, CartValidationException, RestaurantNotFound, FoodPartyExpiration {
        UserManager.getInstance().addToCart(userId, request.getFoodName(), request.getNumber(), request.getRestaurantId(), request.getIsParty(), false);
        return JsonStringCreator.msgCreator( "سفارش شما با موفقیت ثبت شد.");
    }

    @RequestMapping(value = "/v1/cart/finalize", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String finalizeOrder(@RequestAttribute("id") String userId) throws CartValidationException, FoodPartyExpiration, SQLException {
        UserManager.getInstance().finalizeOrder(userId);
        return JsonStringCreator.msgCreator("سفارش شما ثبت نهایی گردید");
    }

    @RequestMapping(value = "/v1/cart", method = RequestMethod.DELETE, produces = "text/plain;charset=UTF-8")
    public String deleteOrder(@RequestAttribute("id") String userId) throws CartValidationException, SQLException {
        UserManager.getInstance().deleteCart(userId);
        return JsonStringCreator.msgCreator("سبد خرید شما با موفقیت حذف شد");
    }

    @RequestMapping(value = "/v1/orders", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getAllCarts(@RequestAttribute("id") String userId) throws SQLException, IOException {
        ArrayList<FinalizedCartDAO> carts = UserManager.getInstance().getAllOrders(userId);
        return mapper.writeValueAsString(ConvertDAOToDTO.finalizedCartsDAO_DTO(carts));
    }

    @RequestMapping(value = "/v1/orders/{cartId}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getCartById(@PathVariable(value = "cartId") String cartId) throws CartNotFound, SQLException {
        ArrayList<OrderDAO> orders = OrderManager.getInstance().getOrdersOfCart(Integer.parseInt(cartId));
        FinalizedCartDAO cart = FinalizedCartRepository.getInstance().getCart(Integer.parseInt(cartId));
        if(cart == null)
            throw new CartNotFound("سبد خریدی با شناسه مورد نظر یافت نشد.");

        return JsonStringCreator.getCartJson(cart, orders);
    }
}
