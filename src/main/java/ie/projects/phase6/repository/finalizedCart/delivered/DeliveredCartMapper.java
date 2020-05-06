package ie.projects.phase6.repository.finalizedCart.delivered;

import ie.projects.phase6.repository.finalizedCart.FinalizedCartDAO;
import ie.projects.phase6.repository.food.FoodDAO;
import ie.projects.phase6.repository.mapper.Mapper;

import java.sql.*;
import java.util.ArrayList;

public class DeliveredCartMapper extends Mapper<FinalizedCartDAO, Integer, String> implements IDeliveredCartMapper {
    private static DeliveredCartMapper instance;
    private static final String TABLE_NAME = "DELIVERED_CART";

    public static String getTableName() {
        return TABLE_NAME;
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
                "CREATE TABLE IF NOT EXISTS %s " +
                        "(cartId INT PRIMARY KEY, " +
                        "userId VARCHAR(255) NOT NULL, " +
                        "restaurantId CHAR(24) NOT NULL, " +
                        "deliveryManId VARCHAR(255) NOT NULL)",
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
                "INSERT INTO %s VALUES (?, ?, ?, ?);",
                TABLE_NAME);
    }


    @Override
    protected void fillInsertStatement(PreparedStatement statement, FinalizedCartDAO cart) throws SQLException{
        statement.setInt(1, cart.getCartId());
        statement.setString(2, cart.getUserId());
        statement.setString(3, cart.getRestaurantId());
        statement.setString(4, cart.getDeliveryManId());
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
        return new FinalizedCartDAO(rs.getInt("cartId"), rs.getString("restaurantId"), 3);
    }

    @Override
    protected ArrayList<FinalizedCartDAO> convertResultSetToObjects(ResultSet rs) throws SQLException {
        ArrayList<FinalizedCartDAO> carts = new ArrayList<>();
        while (rs.next())
            carts.add(new FinalizedCartDAO(rs.getInt("cartId"), rs.getString("restaurantId"), 3));
        return carts;
    }
}
