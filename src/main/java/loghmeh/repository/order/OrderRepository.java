package loghmeh.repository.order;

import loghmeh.domain.exceptions.CartValidationException;
import loghmeh.repository.cart.CartRepository;
import loghmeh.utilities.JsonStringCreator;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderRepository {

    private static OrderRepository instance;
    private IOrderMapper mapper;

    private OrderRepository() throws SQLException
    {
        mapper = OrderMapper.getInstance();
        mapper.createTable();
    }

    public static OrderRepository getInstance() throws SQLException{
        if (instance == null)
            instance = new OrderRepository();
        return instance;
    }

    public void addNewOrder(int cartId, String userId, String restaurantId, String foodName, int foodNum, float price, boolean isParty, boolean isNew) throws SQLException, CartValidationException {
        OrderDAO newOrder = new OrderDAO(cartId, foodName, foodNum, price, isParty);

        Object[] id = new Object[3];
        id[0] = cartId;
        id[1] = foodName;
        id[2] = isParty;

        OrderDAO order;
        try {
            order = mapper.find(id);
        }
        catch (SQLException e1){
            if(!isNew)
                throw new CartValidationException(JsonStringCreator.msgCreator("غذای درخواست‌شده برای تغییر، موجود نمی‌باشد"));
            else if(foodNum <= 0)
                throw new CartValidationException(JsonStringCreator.msgCreator("لطفا عدد مثبتی را وارد نمایید."));

            CartRepository.getInstance().addNewCart(cartId, userId, restaurantId);
//            mapper.updateFoodNum(newOrder);
            mapper.insert(newOrder);
            return;
        }
        if(order.getFoodNum()+foodNum == 0) {
            mapper.delete(id);
            if(mapper.findAllById(cartId).size() == 0)
                CartRepository.getInstance().delete(cartId);
        }
        else if(order.getFoodNum()+foodNum < 0)
            throw new CartValidationException(JsonStringCreator.msgCreator("تعداد درخواستی برای حذف، بیشتر از تعداد انتخاب شده می‌باشد"));
        else {
            CartRepository.getInstance().addNewCart(cartId, userId, restaurantId);
//            mapper.updateFoodNum(newOrder);
            mapper.insert(newOrder);
        }
    }

    public ArrayList<OrderDAO> getOrdersOfCart(int cartId) throws SQLException{
        ArrayList<OrderDAO> orders = this.mapper.findAllById(cartId);
        if(orders.size() == 0)
            return null;
        return orders;
    }

    public void delete(Object[] id) throws SQLException{
        this.mapper.delete(id);
    }

    public void deleteAll(int cartId) throws SQLException{
        this.mapper.deleteAll(cartId);
    }

}
