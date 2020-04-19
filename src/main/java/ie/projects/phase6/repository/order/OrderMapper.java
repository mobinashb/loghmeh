package ie.projects.phase6.repository.order;

import ie.projects.phase6.repository.ConnectionPool;
import ie.projects.phase6.repository.mapper.Mapper;

import java.sql.*;
import java.util.ArrayList;

public class OrderMapper extends Mapper<OrderDAO, Object[], Integer> implements IOrderMapper {
    private static OrderMapper instance;
    private static final String TABLE_NAME = "CART_ORDER";

    @Override
    protected String getFindAllStatement(Integer id)
    {
        return String.format(
                "SELECT * FROM %s WHERE cartId = %d;",
                TABLE_NAME, id.intValue());
    }

    private OrderMapper() {
    }

    public static OrderMapper getInstance(){
        if(instance == null)
            instance = new OrderMapper();
        return instance;
    }

    public void updateFoodNum(OrderDAO obj) throws SQLException {

        String sql = String.format("INSERT %s VALUES(%d, '%s', %d, %f, %b) ON DUPLICATE KEY UPDATE foodNum = foodNum+%d;"
                , TABLE_NAME, obj.getCartId(), obj.getFoodName(), obj.getFoodNum(), obj.getPrice(), obj.getIsParty(), obj.getFoodNum());

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
        return String.format(
                "CREATE TABLE %s " +
                        "(cartId INT NOT NULL, " +
                        "foodName VARCHAR(255) NOT NULL, " +
                        "foodNum INT NOT NULL, " +
                        "price FLOAT NOT NULL, " +
                        "isParty BOOL NOT NULL, " +
                        "PRIMARY KEY (cartId,foodName, isParty));",
                TABLE_NAME);
    }

    @Override
    protected String getFindStatement(Object[] id) {
        return  String.format("SELECT * FROM %s WHERE cartId = %d AND foodName = '%s' AND isParty = %b;", TABLE_NAME, (int)id[0], (String)id[1], (boolean) id[2]);
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
        return String.format("DELETE FROM %s WHERE cartId = %d AND foodName = '%s' AND isParty = %b;", TABLE_NAME, (int)id[0], (String)id[1], (boolean) id[2]);
    }

    @Override
    protected OrderDAO convertResultSetToObject(ResultSet rs) throws SQLException {
        return new OrderDAO(rs.getInt(1), rs.getString(2),
                rs.getInt(3), rs.getFloat(4), rs.getBoolean("isParty"));
    }

    @Override
    protected ArrayList<OrderDAO> convertResultSetToObjects(ResultSet rs) throws SQLException {
        ArrayList<OrderDAO> orders = new ArrayList<>();
        while (rs.next()) {
            orders.add(new OrderDAO(rs.getInt("cartId"), rs.getString("foodName"),
                    rs.getInt("foodNum"), rs.getFloat("price"), rs.getBoolean("isParty")));
        }
        return orders;
    }
}
