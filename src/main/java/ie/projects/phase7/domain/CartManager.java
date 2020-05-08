package ie.projects.phase7.domain;

import ie.projects.phase7.domain.exceptions.CartValidationException;
import ie.projects.phase7.domain.exceptions.FoodPartyExpiration;
import ie.projects.phase7.repository.cart.CartDAO;
import ie.projects.phase7.repository.cart.CartRepository;
import ie.projects.phase7.repository.food.FoodDAO;
import ie.projects.phase7.repository.order.OrderDAO;
import ie.projects.phase7.utilities.JsonStringCreator;

import java.sql.SQLException;
import java.util.ArrayList;

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
            OrderManager.getInstance().addNewOrder(cartId, userId, restaurantId, foodName, foodNum, price, isParty, isNew);
            return;
        }
        throw new CartValidationException(JsonStringCreator.msgCreator("امکان ثبت سفارش از دو رستوران مجزا در یک سبد خرید وجود ندارد"));
    }

    public void clearCartAndOrders(int cartId) throws SQLException{
        this.cartRepository.delete(cartId);
        OrderManager.getInstance().deleteOrdersByCartId(cartId);
    }

    public void clearCart(int cartId) throws SQLException{
        this.cartRepository.delete(cartId);
    }

    public void deleteCartBeforeParty(String restaurantId, String foodName, int cartId) throws SQLException, FoodPartyExpiration{
        CartDAO cart = this.cartRepository.getCart(cartId);
        if(cart == null)
            return;
        if(cart.getRestaurantId().equals(restaurantId)){
            OrderManager.getInstance().deleteOrder(cartId, foodName, true);
            this.clearCart(cartId);
            throw new FoodPartyExpiration(JsonStringCreator.msgCreator("زمان جشن غذا برای غذی انتخاب‌شده به اتمام رسیده‌است. جشن غذاهای افزوده‌شده به سبد خرید پاک می‌شوند"));
        }
    }

    public CartDAO getCartByUserId(String userId) {
        try {
            return this.cartRepository.getCartByUserId(userId);
        }
        catch (SQLException e1){
            return null;
        }
    }

    private boolean hasPartyOrder(ArrayList<OrderDAO> orders){
        for(OrderDAO order: orders) {
            if (order.getIsParty())
                return true;
        }
        return false;
    }

    private FoodDAO findFoodparty(ArrayList<FoodDAO> foods, String foodName){
        for(FoodDAO food: foods){
            if(food.getName().equals(foodName))
                return food;
        }
        return null;
    }

    private void validateCart(ArrayList<OrderDAO> orders, ArrayList<FoodDAO> foods) throws CartValidationException{
        for(OrderDAO order: orders){
            if(order.getIsParty()){
                if(order.getFoodNum() > findFoodparty(foods, order.getFoodName()).getCount())
                    throw new CartValidationException(JsonStringCreator.msgCreator("غذا از جشن غذا برداشته شده‌است و یا موجودی کافی ندارد"));
            }
        }
    }

    public ArrayList<OrderDAO> finalizeOrder(int cartId, String restaurantId) throws SQLException, CartValidationException, FoodPartyExpiration{
        ArrayList<OrderDAO> orders = OrderManager.getInstance().getOrdersOfCart(cartId);
        if(orders == null)
            throw new CartValidationException(JsonStringCreator.msgCreator("سفارشی برای ثبت نهایی موجود نمی‌باشد"));

        if(hasPartyOrder(orders)){
            ArrayList<FoodDAO> foods = FoodpartyManager.getInstance().getFoodpartyByRestaurantId(restaurantId);
            if(foods.size() == 0){
                CartManager.getInstance().clearCart(cartId);
                OrderManager.getInstance().deleteOrdersByCartId(cartId);
                throw new FoodPartyExpiration(JsonStringCreator.msgCreator("زمان جشن غذا برای غذای انتخاب‌شده به اتمام رسیده‌است. جشن غذاهای افزوده‌شده به سبد خرید پاک می‌شوند"));
            }
            validateCart(orders, foods);
        }
        return orders;
    }

    public float getCartPrice(ArrayList<OrderDAO> orders){
        float price = 0;
        for(OrderDAO order: orders)
            price += order.getPrice() * order.getFoodNum();
        return price;
    }

}
