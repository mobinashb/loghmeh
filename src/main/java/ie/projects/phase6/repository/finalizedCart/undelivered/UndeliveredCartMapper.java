package ie.projects.phase6.repository.finalizedCart.undelivered;

import ie.projects.phase6.repository.finalizedCart.FinalizedCartDAO;
import ie.projects.phase6.repository.mapper.Mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UndeliveredCartMapper extends Mapper<FinalizedCartDAO, Integer, String> implements IUndeliveredCartMapper {
    private static UndeliveredCartMapper instance;
    private static final String TABLE_NAME = "UNDELIVERED_CART";

    @Override
    protected String getFindAllStatement(String id) {
        return null;
//        return String.format(
//                "SELECT * FROM %s WHERE userId = '%s';",
//                TABLE_NAME, id);
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
                "CREATE TABLE %s " +
                        "(cartId INT PRIMARY KEY, " +
                        "userId VARCHAR(255) NOT NULL, " +
                        "restaurantId CHAR(24) NOT NULL, " +
                        "deliveryManId VARCHAR(255), " +
                        "orderStatus SMALLINT NOT NULL, " +
                        "deliveryManTimeToReach BIGINT, " +
                        "deliveryManFoundedTime DOUBLE)",
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
