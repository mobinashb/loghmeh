package ie.projects.phase6.repository.order;

import ie.projects.phase6.domain.exceptions.CartValidationException;
import ie.projects.phase6.repository.ConnectionPool;
import ie.projects.phase6.repository.mapper.Mapper;

import java.sql.*;
import java.util.ArrayList;

public class OrderMapper extends Mapper<OrderDAO, Object[]> implements IOrderMapper {
    private static OrderMapper instance;
    private static final String TABLE_NAME = "CART_ORDER";

    @Override
    protected String getFindAllStatement(Object[] id) {
        return null;
    }

    private OrderMapper() {
    }

    public static OrderMapper getInstance(){
        if(instance == null)
            instance = new OrderMapper();
        return instance;
    }

    public void updateFoodNum(OrderDAO obj) throws SQLException {

        String sql = String.format("INSERT %s VALUES(%d, '%s', %d, %f) ON DUPLICATE KEY UPDATE foodNum = foodNum+%d;"
                , TABLE_NAME, obj.getCartId(), obj.getFoodName(), obj.getFoodNum(), obj.getPrice(), obj.getFoodNum());

        try (Connection con = ConnectionPool.getConnection();
             Statement st = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)
        ) {
            try {
                st.executeUpdate(sql);

            } catch (SQLException ex) {
                System.out.println("error in update order query.");
                throw ex;
            }
        }
    }

    @Override
    protected String getDeleteTableStatement(){
        return "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }

    @Override
    protected String getCreateTableStatement(){
        String ali = String.format(
                "CREATE TABLE %s " +
                        "(cartId INT NOT NULL, " +
                        "foodName VARCHAR(255) NOT NULL, " +
                        "foodNum INT NOT NULL, " +
                        "price FLOAT NOT NULL, " +
                        "PRIMARY KEY (cartId,foodName));",
                TABLE_NAME);
        System.out.println(ali);
        return ali;
    }

    @Override
    protected String getFindStatement(Object[] id) {
        return  String.format("SELECT * FROM %s WHERE cartId = %d AND foodName = '%s';", TABLE_NAME, (int)id[0], (String)id[1]);
    }

    @Override
    protected String getInsertStatement(OrderDAO user) {
        return null;
//        return String.format(
//                "INSERT INTO %s VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');",
//                TABLE_NAME, user.getId(), user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getEmail(), user.getCredit(), user.getLocationX(), user.getLocationY());
    }

    @Override
    protected String getPreparedInsertStatement(){
        return null;
    }

    @Override
    protected PreparedStatement fillPreparedInsertStatement(PreparedStatement statement, OrderDAO user){
        return null;
    }

    @Override
    protected String getDeleteStatement(Object[] id) {
        return String.format("DELETE FROM %s WHERE cartId = %d AND foodName = '%s';", TABLE_NAME, (int)id[0], (String)id[1]);
    }

    @Override
    protected OrderDAO convertResultSetToObject(ResultSet rs) throws SQLException {
        return new OrderDAO(rs.getInt(1), rs.getString(2),
                rs.getInt(3), rs.getFloat(4));
    }

    @Override
    protected ArrayList<OrderDAO> convertResultSetToObjects(ResultSet rs) throws SQLException {
        return null;
    }
}
