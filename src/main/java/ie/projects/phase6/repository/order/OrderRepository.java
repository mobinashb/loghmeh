package ie.projects.phase6.repository.order;

import ie.projects.phase6.domain.exceptions.CartValidationException;
import ie.projects.phase6.repository.cart.CartRepository;
import ie.projects.phase6.utilities.JsonStringCreator;

import java.sql.SQLException;

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

    public void addNewOrder(int cartId, String foodName, int foodNum, float price, boolean isNew) throws SQLException, CartValidationException {
        OrderDAO newOrder = new OrderDAO(cartId, foodName, foodNum, price);

        Object[] id = new Object[2];
        id[0] = cartId;
        id[1] = foodName;

        OrderDAO order;
        try {
            order = mapper.find(id);
        }
        catch (SQLException e1){
            if(!isNew)
                throw new CartValidationException(JsonStringCreator.msgCreator("غذای درخواست‌شده برای تغییر، موجود نمی‌باشد"));
            else if(foodNum <= 0)
                throw new CartValidationException(JsonStringCreator.msgCreator("لطفا عدد مثبتی را وارد نمایید."));
            mapper.updateFoodNum(newOrder);
            return;
        }
        if(order.getFoodNum()+foodNum == 0) {
            mapper.delete(id);
            CartRepository.getInstance().delete(cartId);
        }
        else if(order.getFoodNum()+foodNum < 0)
            throw new CartValidationException(JsonStringCreator.msgCreator("تعداد درخواستی برای حذف، بیشتر از تعداد انتخاب شده می‌باشد"));
        else
            mapper.updateFoodNum(newOrder);
    }
}
