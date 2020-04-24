package ie.projects.phase6.repository.finalizedCart;

import ie.projects.phase6.domain.repeatedTasks.CheckOrderStatus;
import ie.projects.phase6.domain.repeatedTasks.UpdateFoodParty;
import ie.projects.phase6.repository.cart.CartDAO;
import ie.projects.phase6.repository.finalizedCart.delivered.DeliveredCartMapper;
import ie.projects.phase6.repository.finalizedCart.delivered.IDeliveredCartMapper;
import ie.projects.phase6.repository.finalizedCart.undelivered.IUndeliveredCartMapper;
import ie.projects.phase6.repository.finalizedCart.undelivered.UndeliveredCartMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;

public class FinalizedCartRepository {

    private static FinalizedCartRepository instance;
    private static final long CHECK_ORDER_STATUS_PERIOD = 3000;

    private IDeliveredCartMapper deliveredMapper;
    private IUndeliveredCartMapper undeliveredMapper;
    private boolean schedulerSet = false;

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

    public void checkOrdersStates(){
        this.undeliveredMapper.checkState();
    }

    public void addNewCart(CartDAO cart) throws SQLException{
        FinalizedCartDAO finalizedCartDAO = new FinalizedCartDAO(cart.getCartId(), cart.getUserId(), cart.getRestaurantId(), "", 1, 0, 0);
        this.undeliveredMapper.insert(finalizedCartDAO);
    }

    public ArrayList<FinalizedCartDAO> getDeliveredOrders(String userId) throws SQLException{
        return this.deliveredMapper.findAllById(userId);
    }

    public ArrayList<FinalizedCartDAO> getUndeliveredOrders(String userId) throws SQLException{
        return this.undeliveredMapper.findAllById(userId);
    }

    public FinalizedCartDAO getCart(int cartId){
        try{
            return this.deliveredMapper.find(cartId);
        }
        catch (SQLException e1){
            try {
                return this.undeliveredMapper.find(cartId);
            }
            catch (SQLException e2){
                return null;
            }
        }
    }

    public int getLastId() throws SQLException{
        return this.undeliveredMapper.getMaxId(DeliveredCartMapper.getTableName());
    }

}
