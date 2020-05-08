package ie.projects.phase7.domain;

import ie.projects.phase7.domain.exceptions.CartValidationException;
import ie.projects.phase7.repository.order.OrderDAO;
import ie.projects.phase7.repository.order.OrderRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderManager {
    private static OrderManager instance;

    private OrderRepository orderRepository;

    private OrderManager() throws SQLException {
        this.orderRepository = OrderRepository.getInstance();
    }

    public static OrderManager getInstance() throws SQLException {
        if (instance == null)
            instance = new OrderManager();
        return instance;
    }

    public ArrayList<OrderDAO> getOrdersOfCart(int cartId){
        try {
            return this.orderRepository.getOrdersOfCart(cartId);
        }
        catch (SQLException e1){
            return null;
        }
    }

    public void addNewOrder(int cartId, String userId, String restaurantId, String foodName, int foodNum, float price, boolean isParty, boolean isNew) throws CartValidationException, SQLException {
        this.orderRepository.addNewOrder(cartId, userId, restaurantId, foodName, foodNum, price, isParty, isNew);
    }

    public void deleteOrder(int cartId, String foodName, boolean isParty) throws SQLException{
        Object[] id = new Object[3];
        id[0] = cartId;
        id[1] = foodName;
        id[2] = isParty;
        this.orderRepository.getInstance().delete(id);
    }

    public void deleteOrdersByCartId(int cartId) throws SQLException{
        this.orderRepository.deleteAll(cartId);
    }

}
