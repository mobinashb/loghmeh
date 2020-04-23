package ie.projects.phase6.repository.finalizedCart.undelivered;

import ie.projects.phase6.domain.LoghmehManger;
import ie.projects.phase6.domain.foreignServiceObjects.DeliveryMan;
import ie.projects.phase6.domain.exceptions.RestaurantNotFound;
import ie.projects.phase6.repository.ConnectionPool;
import ie.projects.phase6.repository.finalizedCart.FinalizedCartDAO;
import ie.projects.phase6.repository.finalizedCart.delivered.DeliveredCartMapper;
import ie.projects.phase6.repository.mapper.Mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UndeliveredCartMapper extends Mapper<FinalizedCartDAO, Integer, String> implements IUndeliveredCartMapper {
    private static UndeliveredCartMapper instance;
    private static final String TABLE_NAME = "UNDELIVERED_CART";

    @Override
    protected String getFindAllStatement(String field) {
        return String.format(
                "SELECT * FROM %s WHERE userId = '%s';",
                TABLE_NAME, field);
    }

    private UndeliveredCartMapper() {
    }

    public static UndeliveredCartMapper getInstance(){
        if(instance == null)
            instance = new UndeliveredCartMapper();
        return instance;
    }

    @Override
    protected String getDeleteTableStatement(){
        return "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }

    @Override
    protected String getCreateTableStatement(){
        return String.format(
                "CREATE TABLE IF NOT EXISTS %s " +
                        "(cartId INT PRIMARY KEY, " +
                        "userId VARCHAR(255) NOT NULL, " +
                        "restaurantId CHAR(24) NOT NULL, " +
                        "deliveryManId VARCHAR(255), " +
                        "orderStatus int NOT NULL, " +
                        "deliveryManTimeToReach DOUBLE, " +
                        "deliveryManFoundedTime DOUBLE)",
                        TABLE_NAME);
    }

    @Override
    protected String getFindStatement(Integer id) {
        return String.format("SELECT * FROM %s WHERE cartId = %d;", TABLE_NAME, id.intValue());
    }

    @Override
    protected String getInsertStatement(FinalizedCartDAO cart) {
        return String.format(
                "INSERT INTO %s(cartId, userId, restaurantId, orderStatus) VALUES (%d, '%s', '%s', %d);",
                TABLE_NAME, cart.getCartId(), cart.getUserId(), cart.getRestaurantId(), 1);
    }

    @Override
    protected String getPreparedInsertStatement(){
        return null;
    }

    @Override
    protected PreparedStatement fillPreparedInsertStatement(PreparedStatement statement, FinalizedCartDAO cart){
        return null;
    }

    @Override
    protected String getDeleteStatement(Integer id) {
        return null;
    }

    @Override
    protected String getDeleteAllStatement(String id){
        return null;
    }

    @Override
    protected FinalizedCartDAO convertResultSetToObject(ResultSet rs) throws SQLException {
        return new FinalizedCartDAO(rs.getInt("cartId"), rs.getString("restaurantId"), rs.getInt("orderStatus"));
    }

    @Override
    protected ArrayList<FinalizedCartDAO> convertResultSetToObjects(ResultSet rs) throws SQLException {
        ArrayList<FinalizedCartDAO> carts = new ArrayList<>();
        while (rs.next())
            carts.add(new FinalizedCartDAO(rs.getInt("cartId"), rs.getString("restaurantId"), rs.getInt("orderStatus")));
        return carts;
    }

    public void checkState(){
        String sql = "SELECT * FROM " + TABLE_NAME;

        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet rs = st.executeQuery()
        ) {
            while (rs.next()) {
                if(rs.getInt("orderStatus") == 1){
                    DeliveryMan deliveryMan = LoghmehManger.getInstance().selectDeliveryManForOrder(rs.getString("userId"), rs.getString("restaurantId"));
//                    System.out.println(">");
                    if(deliveryMan == null)
                        continue;
//                    System.out.println("???????????????????????????????????????????");
//                    System.out.println(deliveryMan.getTimeToReach());
                    rs.updateInt("orderStatus", 2);
                    rs.updateString("deliveryManId", deliveryMan.getId());
                    rs.updateDouble("deliveryManTimeToReach", deliveryMan.getTimeToReach());
                    rs.updateDouble("deliveryManFoundedTime", (double) System.currentTimeMillis());
                    rs.updateRow();
                }
                else if(rs.getInt("orderStatus") == 2){
                    if ((System.currentTimeMillis() - rs.getDouble("deliveryManFoundedTime")) > (rs.getDouble("deliveryManTimeToReach") * 1000)) {
                        rs.updateInt("orderStatus", 3);
                        rs.updateRow();
                    }
                }
                else if(rs.getInt("orderStatus") == 3){
                    FinalizedCartDAO cart = new FinalizedCartDAO(rs.getInt("cartId"), rs.getString("userId"),
                            rs.getString("restaurantId"), rs.getString("deliveryManId"));
                    DeliveredCartMapper.getInstance().insert(cart);
                    rs.deleteRow();
                }
            }
        }
        catch (SQLException e1){
            e1.printStackTrace();
        }
        catch (RestaurantNotFound e1){
            e1.printStackTrace();
        }
    }
}
