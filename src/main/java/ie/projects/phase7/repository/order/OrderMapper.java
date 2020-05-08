package ie.projects.phase7.repository.order;

import ie.projects.phase7.configs.DatabaseTablesName;
import ie.projects.phase7.repository.mapper.Mapper;

import java.sql.*;
import java.util.ArrayList;

public class OrderMapper extends Mapper<OrderDAO, Object[], Integer> implements IOrderMapper {
    private static OrderMapper instance;
    private static final String TABLE_NAME = DatabaseTablesName.ORDERS_TABLE;

    private OrderMapper() {
    }

    public static OrderMapper getInstance() {
        if (instance == null)
            instance = new OrderMapper();
        return instance;
    }

    @Override
    protected String getCreateTableStatement(){
        return String.format(
                "CREATE TABLE IF NOT EXISTS %s " +
                        "(cartId INT NOT NULL, " +
                        "foodName VARCHAR(255) NOT NULL, " +
                        "foodNum INT NOT NULL, " +
                        "price FLOAT NOT NULL, " +
                        "isParty BOOL NOT NULL, " +
                        "PRIMARY KEY (cartId,foodName, isParty));",
                TABLE_NAME);
    }

    @Override
    protected String getDeleteTableStatement(){
        return "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }

    @Override
    protected String getFindByIdStatement() {
        return  String.format("SELECT * FROM %s WHERE cartId = ? AND foodName = ? AND isParty = ?;", TABLE_NAME);
    }

    @Override
    protected void fillFindByIdStatement(PreparedStatement statement, Object[] id) throws SQLException{
        statement.setInt(1, (int)id[0]);
        statement.setString(2, (String) id[1]);
        statement.setBoolean(3, (boolean)id[2]);

    }

    @Override
    protected String getFindAllStatement()
    {
        return String.format(
                "SELECT * FROM %s WHERE cartId = ?;",
                TABLE_NAME);
    }

    @Override
    protected void fillFindAllStatement(PreparedStatement statement, Integer field) throws SQLException{
        statement.setInt(1, field);
    }


    @Override
    protected String getInsertStatement() {
        return String.format("INSERT %s VALUES(?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE foodNum = foodNum + ?;", TABLE_NAME);
    }

    @Override
    protected void fillInsertStatement(PreparedStatement statement, OrderDAO order) throws SQLException{
        statement.setInt(1, order.getCartId());
        statement.setString(2, order.getFoodName());
        statement.setInt(3, order.getFoodNum());
        statement.setFloat(4, order.getPrice());
        statement.setBoolean(5, order.getIsParty());
        statement.setInt(6, order.getFoodNum());
    }

    @Override
    protected String getInsertAllStatement(){
        return null;
    }

    @Override
    protected PreparedStatement fillInsertAllStatement(PreparedStatement statement, OrderDAO user){
        return null;
    }

    @Override
    protected String getDeleteStatement() {
        return String.format("DELETE FROM %s WHERE cartId = ? AND foodName = ? AND isParty = ?;", TABLE_NAME);
    }


    @Override
    protected void fillDeleteStatement(PreparedStatement statement, Object[] id) throws SQLException{
        statement.setInt(1, (int)id[0]);
        statement.setString(2, (String)id[1]);
        statement.setBoolean(3, (boolean)id[2]);
    }


    @Override
    protected String getDeleteAllStatement(){
        return String.format("DELETE FROM %s WHERE cartId = ?", TABLE_NAME);
    }

    @Override
    protected void fillDeleteAllStatement(PreparedStatement statement, Integer cartId) throws SQLException{
        statement.setInt(1, cartId);
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



//    public void updateFoodNum(OrderDAO obj) throws SQLException {
//
//        String sql = String.format("INSERT %s VALUES(%d, '%s', %d, %f, %b) ON DUPLICATE KEY UPDATE foodNum = foodNum+%d;"
//                , TABLE_NAME, obj.getCartId(), obj.getFoodName(), obj.getFoodNum(), obj.getPrice(), obj.getIsParty(), obj.getFoodNum());
//
//        try (Connection con = ConnectionPool.getConnection();
//             Statement st = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)
//        ) {
//            try {
//                st.executeUpdate(sql);
//
//            } catch (SQLException ex) {
//                System.out.println("error in update order query.");
//                throw ex;
//            }
//        }
//    }

}
