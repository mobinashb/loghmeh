package ie.projects.phase6.repository.finalizedCart.delivered;

import ie.projects.phase6.repository.finalizedCart.FinalizedCartDAO;
import ie.projects.phase6.repository.mapper.Mapper;

import java.sql.*;
import java.util.ArrayList;

public class DeliveredCartMapper extends Mapper<FinalizedCartDAO, Integer, String> implements IDeliveredCartMapper {
    private static DeliveredCartMapper instance;
    private static final String TABLE_NAME = "DELIVERED_CART";

    @Override
    protected String getFindAllStatement(String id) {
        return null;
//        return String.format(
//                "SELECT * FROM %s WHERE userId = '%s';",
//                TABLE_NAME, id);
    }

    private DeliveredCartMapper() {
    }

    public static DeliveredCartMapper getInstance(){
        if(instance == null)
            instance = new DeliveredCartMapper();
        return instance;
    }

    @Override
    protected String getDeleteTableStatement(){
        return "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }

    @Override
    protected String getCreateTableStatement(){
        return String.format(
                "CREATE TABLE %s " +
                        "(cartId INT PRIMARY KEY, " +
                        "userId VARCHAR(255) NOT NULL, " +
                        "restaurantId CHAR(24) NOT NULL, " +
                        "deliveryManId VARCHAR(255) NOT NULL)",
                TABLE_NAME);
    }

    @Override
    protected String getFindStatement(Integer id) {
        return null;
//        return String.format("SELECT * FROM %s WHERE id = %d;", TABLE_NAME, id.intValue());
    }

    @Override
    protected String getInsertStatement(FinalizedCartDAO cart) {
        return String.format(
                "INSERT INTO %s VALUES (%d, '%s', '%s', '%s');",
                TABLE_NAME, cart.getCartId(), cart.getUserId(), cart.getRestaurantId(), cart.getDeliveryManId());
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
//        return String.format("DELETE FROM %s WHERE id = %d;", TABLE_NAME, id.intValue());
    }

    @Override
    protected String getDeleteAllStatement(String id){
        return null;
    }


    @Override
    protected FinalizedCartDAO convertResultSetToObject(ResultSet rs) throws SQLException {
        return null;
//        return new FinalizedCartDAO(rs.getInt("id"), rs.getString("userId"), rs.getString("restaurantId"));
    }

    @Override
    protected ArrayList<FinalizedCartDAO> convertResultSetToObjects(ResultSet rs) throws SQLException {
        return null;
//        ArrayList<FinalizedCartDAO> carts = new ArrayList<>();
//        while (rs.next()) {
//            carts.add(new FinalizedCartDAO(rs.getInt("id"), rs.getString("userId"), rs.getString("restaurantId")));
//        }
//        return carts;
    }
}
