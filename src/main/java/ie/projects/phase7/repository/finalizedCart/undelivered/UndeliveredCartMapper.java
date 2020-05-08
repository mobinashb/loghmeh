package ie.projects.phase7.repository.finalizedCart.undelivered;

import ie.projects.phase7.configs.DatabaseTablesName;
import ie.projects.phase7.domain.LoghmehManger;
import ie.projects.phase7.domain.foreignServiceObjects.DeliveryMan;
import ie.projects.phase7.domain.exceptions.RestaurantNotFound;
import ie.projects.phase7.repository.ConnectionPool;
import ie.projects.phase7.repository.finalizedCart.FinalizedCartDAO;
import ie.projects.phase7.repository.finalizedCart.delivered.DeliveredCartMapper;
import ie.projects.phase7.repository.mapper.Mapper;

import java.sql.*;
import java.util.ArrayList;

public class UndeliveredCartMapper extends Mapper<FinalizedCartDAO, Integer, String> implements IUndeliveredCartMapper {
    private static UndeliveredCartMapper instance;
    private static final String TABLE_NAME = DatabaseTablesName.UNDELIVERED_CART_TABLE;

    public static String getTableName() {
        return TABLE_NAME;
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
    protected String getFindByIdStatement() {
        return String.format("SELECT * FROM %s WHERE cartId = ?;", TABLE_NAME);
    }

    @Override
    protected void fillFindByIdStatement(PreparedStatement statement, Integer id) throws SQLException{
        statement.setInt(1, id);
    }

    @Override
    protected String getFindAllStatement() {
        return String.format(
                "SELECT * FROM %s WHERE userId = ?;",
                TABLE_NAME);
    }

    @Override
    protected void fillFindAllStatement(PreparedStatement statement, String field) throws SQLException{
        statement.setString(1, field);
    }

    @Override
    protected String getInsertStatement() {
        return String.format(
                "INSERT INTO %s (cartId, userId, restaurantId, orderStatus) VALUES (?, ?, ?, ?);",
                TABLE_NAME);
    }

    @Override
    protected void fillInsertStatement(PreparedStatement statement, FinalizedCartDAO cart) throws SQLException{
        statement.setInt(1, cart.getCartId());
        statement.setString(2, cart.getUserId());
        statement.setString(3, cart.getRestaurantId());
        statement.setInt(4, 1);
    }

    @Override
    protected String getInsertAllStatement(){
        return null;
    }

    @Override
    protected PreparedStatement fillInsertAllStatement(PreparedStatement statement, FinalizedCartDAO cart){
        return null;
    }

    @Override
    protected String getDeleteStatement() {
        return null;
    }

    @Override
    protected void fillDeleteStatement(PreparedStatement statement, Integer id) throws SQLException{
        return;
    }

    @Override
    protected String getDeleteAllStatement(){
        return null;
    }

    @Override
    protected void fillDeleteAllStatement(PreparedStatement statement, String field) throws SQLException{
        return;
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
                    if(deliveryMan == null)
                        continue;
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

    public int getMaxId(String deliveredCartTableName) throws SQLException {
        String sql = String.format("SELECT IFNULL(MAX(cartId), 0) cartId FROM (SELECT cartId FROM %s " +
                                    "UNION ALL SELECT cartId FROM %s) a"
                , TABLE_NAME, deliveredCartTableName);

        try (Connection con = ConnectionPool.getConnection();
             Statement st = con.createStatement()
        ) {
            ResultSet rs;
            try {
                rs = st.executeQuery(sql);
                if(rs.next())
                    return rs.getInt(1);
            } catch (SQLException ex) {
                System.out.println("error in getMazId query.");
                throw ex;
            }
        }
        return -1;
    }
}
