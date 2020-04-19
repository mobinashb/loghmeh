package ie.projects.phase6.domain;

import ie.projects.phase6.domain.exceptions.CartValidationException;
import ie.projects.phase6.domain.exceptions.FoodPartyExpiration;
import ie.projects.phase6.repository.cart.CartDAO;
import ie.projects.phase6.repository.cart.CartRepository;
import ie.projects.phase6.repository.order.OrderRepository;
import ie.projects.phase6.utilities.JsonStringCreator;

import java.sql.SQLException;

public class CartManager {
    private static CartManager instance;

    private CartRepository cartRepository;

    private CartManager() throws SQLException {
        this.cartRepository = CartRepository.getInstance();
    }

    public static CartManager getInstance() throws SQLException {
        if (instance == null)
            instance = new CartManager();
        return instance;
    }

    public void addToCart(int cartId, String userId, String restaurantId, String foodName, int foodNum, float price, boolean isParty, boolean isNew) throws SQLException, CartValidationException{
        if(this.cartRepository.checkRestaurantEqualityForCart(cartId, restaurantId)){
            if(foodNum == 0)
                throw new CartValidationException(JsonStringCreator.msgCreator("لطفا عدد مثبتی را وارد نمایید"));
            CartRepository.getInstance().addNewCart(cartId, userId, restaurantId);
            OrderRepository.getInstance().addNewOrder(cartId, foodName, foodNum, price, isParty, isNew);
//            FoodpartyRepository.getInstance().updateFoodpartyCount(restaurantId, foodName, foodNum);
            return;
        }
        throw new CartValidationException(JsonStringCreator.msgCreator("امکان ثبت سفارش از دو رستوران مجزا در یک سبد خرید وجود ندارد"));
    }

    public void deleteCartBeforeParty(String restaurantId, String foodName, int cartId) throws SQLException, FoodPartyExpiration{
        CartDAO cart = this.cartRepository.findCartById(cartId);
        if(cart == null)
            return;
        if(cart.getRestaurantId().equals(restaurantId)){
            Object[] id = new Object[2];
            id[0] = cartId;
            id[1] = foodName;
            OrderRepository.getInstance().delete(id);
            this.cartRepository.delete(cartId);
            throw new FoodPartyExpiration(JsonStringCreator.msgCreator("زمان جشن غذا برای غذی انتخاب‌شده به اتمام رسیده‌است. جشن غذاهای افزوده‌شده به سبد خرید پاک می‌شوند"));
        }
    }

    public CartDAO getCartByUserId(String userId) throws SQLException{
        return this.cartRepository.getCartByUserId(userId);
    }


}
