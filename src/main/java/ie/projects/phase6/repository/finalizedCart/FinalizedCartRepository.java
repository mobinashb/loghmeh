package ie.projects.phase6.repository.finalizedCart;

import ie.projects.phase6.repository.cart.CartDAO;
import ie.projects.phase6.repository.finalizedCart.delivered.DeliveredCartMapper;
import ie.projects.phase6.repository.finalizedCart.delivered.IDeliveredCartMapper;
import ie.projects.phase6.repository.finalizedCart.undelivered.IUndeliveredCartMapper;
import ie.projects.phase6.repository.finalizedCart.undelivered.UndeliveredCartMapper;

import java.sql.SQLException;

public class FinalizedCartRepository {

    private static FinalizedCartRepository instance;
    private IDeliveredCartMapper deliveredMapper;
    private IUndeliveredCartMapper undeliveredMapper;

    private FinalizedCartRepository() throws SQLException
    {
        deliveredMapper = DeliveredCartMapper.getInstance();
        deliveredMapper.createTable();

        undeliveredMapper = UndeliveredCartMapper.getInstance();
        undeliveredMapper.createTable();
    }

    public static FinalizedCartRepository getInstance() throws SQLException{
        if (instance == null)
            instance = new FinalizedCartRepository();
        return instance;
    }

    public void addNewCart(CartDAO cart) throws SQLException{
        FinalizedCartDAO finalizedCartDAO = new FinalizedCartDAO(cart.getCartId(), cart.getUserId(), cart.getRestaurantId(), "", 1, 0, 0);
        this.undeliveredMapper.insert(finalizedCartDAO);
    }

}
