package ie.projects.phase6.repository.cart;

import ie.projects.phase6.repository.ConnectionPool;
import ie.projects.phase6.repository.food.FoodDAO;
import ie.projects.phase6.repository.mapper.Mapper;

import java.sql.*;
import java.util.ArrayList;

public class CartMapper extends Mapper<CartDAO, Integer, String> implements ICartMapper {
    private static CartMapper instance;
    private static final String TABLE_NAME = "CART";

    private CartMapper() {
    }

    public static CartMapper getInstance(){
        if(instance == null)
            instance = new CartMapper();
        return instance;
    }

    @Override
    protected String getDeleteTableStatement(){
        return "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }

    @Override
    protected String getCreateTableStatement(){
        return String.format(
                "CREATE TABLE IF NOT EXISTS  %s " +
                        "(id INT PRIMARY KEY, " +
                        "userId VARCHAR(255) NOT NULL, " +
                        "restaurantId VARCHAR(255) NOT NULL);",
                TABLE_NAME);
    }

    @Override
    protected String getFindByIdStatement() {
        return String.format("SELECT * FROM %s WHERE id = ?;", TABLE_NAME);
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
                "INSERT IGNORE INTO %s VALUES (?, ?, ?);",
                TABLE_NAME);
    }

    @Override
    protected void fillInsertStatement(PreparedStatement statement, CartDAO cart) throws SQLException{
        statement.setInt(1, cart.getCartId());
        statement.setString(2, cart.getUserId());
        statement.setString(3, cart.getRestaurantId());
    }

    @Override
    protected String getInsertAllStatement(){
        return null;
    }

    @Override
    protected PreparedStatement fillInsertAllStatement(PreparedStatement statement, CartDAO user){
        return null;
    }

    @Override
    protected String getDeleteStatement() {
        return String.format("DELETE FROM %s WHERE id = ?;", TABLE_NAME);
    }

    @Override
    protected void fillDeleteStatement(PreparedStatement statement, Integer id) throws SQLException{
        statement.setInt(1, id);
    }

    @Override
    protected String getDeleteAllStatement(){
        return null;
    }

    @Override
    protected void fillDeleteAllStatement(PreparedStatement statement, String field) throws SQLException{
        return;
    }

    public boolean checkRestaurantEqualityForCart(int cartId, String restaurantId) throws SQLException{
        String sql = String.format("SELECT * FROM %s WHERE id = %d;", TABLE_NAME, cartId);

        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(sql)
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                if(resultSet.next())
                    return resultSet.getString(3).equals(restaurantId);
                return true;

            } catch (SQLException ex) {
                System.out.println("error in checkRestaurantEqualityForCart query.");
                throw ex;
            }
        }
    }

    public int getMaxId() throws SQLException {
        String sql = String.format("SELECT MAX(id) AS 'MAXIMUM' FROM %s;", TABLE_NAME);

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

    @Override
    protected CartDAO convertResultSetToObject(ResultSet rs) throws SQLException {
        return new CartDAO(rs.getInt("id"), rs.getString("userId"), rs.getString("restaurantId"));
    }

    @Override
    protected ArrayList<CartDAO> convertResultSetToObjects(ResultSet rs) throws SQLException {
        ArrayList<CartDAO> carts = new ArrayList<>();
        while (rs.next()) {
            carts.add(new CartDAO(rs.getInt("id"), rs.getString("userId"), rs.getString("restaurantId")));
        }
        return carts;
    }
}
