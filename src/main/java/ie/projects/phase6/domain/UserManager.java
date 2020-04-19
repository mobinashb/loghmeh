package ie.projects.phase6.domain;


import ie.projects.phase6.domain.core.Order;
import ie.projects.phase6.domain.exceptions.CartValidationException;
import ie.projects.phase6.domain.exceptions.FoodPartyExpiration;
import ie.projects.phase6.domain.exceptions.NegativeCreditAmount;
import ie.projects.phase6.domain.exceptions.RestaurantNotFound;
import ie.projects.phase6.repository.cart.CartDAO;
import ie.projects.phase6.repository.food.FoodDAO;
import ie.projects.phase6.repository.order.OrderDAO;
import ie.projects.phase6.repository.restaurant.RestaurantDAO;
import ie.projects.phase6.repository.user.UserDAO;
import ie.projects.phase6.repository.user.UserRepository;
import ie.projects.phase6.utilities.JsonStringCreator;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserManager {
    private static UserManager instance;

    private UserRepository userRepository;
    private int cartIdGenerator;

    private UserManager() throws SQLException {
        this.userRepository = UserRepository.getInstance();
        this.userRepository.insertTempUser();
        this.cartIdGenerator = 0;
    }

    public static UserManager getInstance() throws SQLException {
        if (instance == null)
            instance = new UserManager();
        return instance;
    }

    public UserDAO getUserById(String id) throws SQLException{
        return this.userRepository.findUser(id);
    }

    public void addCredit(String userId, float amount) throws NegativeCreditAmount, SQLException{
        if(amount <= 0)
            throw new NegativeCreditAmount(JsonStringCreator.msgCreator("برای افزایش اعتبار مقدار مثبتی را وارد نمایید"));
        this.userRepository.addCredit(userId, amount);
    }

    public void addToCart(String userId, String foodName, int foodNum, String restaurantId, boolean isParty, boolean isNew) throws CartValidationException, RestaurantNotFound, FoodPartyExpiration, SQLException {

        CartManager cartManager = CartManager.getInstance();
        OrderManager.getInstance();
        float price;
        if(isParty){
            FoodDAO foodDAO = FoodpartyManager.getInstance().findFoodpartyById(restaurantId, foodName);
            if(foodDAO == null) {
                cartManager.deleteCartBeforeParty(restaurantId, foodName, this.cartIdGenerator);
                throw new RestaurantNotFound(JsonStringCreator.msgCreator("رستوران با شناسه درخواست‌شده، شامل غذای درخواست‌شده برای جشن غذا نمی‌باشد"));
            }
            if(foodDAO.getCount() < foodNum)
                throw new CartValidationException(JsonStringCreator.msgCreator("غذای درخواست‌شده از جشن غذا موجودی خواسته‌شده را ندارد."));
            price = foodDAO.getPrice();
        }
        else {
            RestaurantDAO restaurant = RestaurantManager.getInstance().findRestaurantById(restaurantId);
            if (restaurant == null)
                throw new RestaurantNotFound(JsonStringCreator.msgCreator("رستورانی با شناسه درخواست‌شده موجود نمی‌باشد"));
            FoodDAO food = FoodManager.getInstance().findFood(restaurantId, foodName);
            if(food == null)
                throw new CartValidationException(JsonStringCreator.msgCreator("رستوران مدنظر، شامل غذای درخواست‌شده نمی‌باشد"));
            price = food.getPrice();
        }
        cartManager.addToCart(this.cartIdGenerator, userId, restaurantId, foodName, foodNum, price, isParty, isNew);
    }

    public Object[] getCart(String userId) throws SQLException{
        CartDAO cart = CartManager.getInstance().getCartByUserId(userId);
        ArrayList<OrderDAO> orders = new ArrayList<>();
        if(cart != null)
            orders = OrderManager.getInstance().getOrdersOfCart(cart.getCartId());
        Object[] resultCart = new Object[2];
        resultCart[0] = cart;
        resultCart[1] = orders;
        return resultCart;
    }
}
