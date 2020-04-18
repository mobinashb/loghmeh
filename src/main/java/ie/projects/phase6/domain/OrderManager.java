package ie.projects.phase6.domain;

import ie.projects.phase6.repository.order.OrderRepository;

import java.sql.SQLException;

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
}
