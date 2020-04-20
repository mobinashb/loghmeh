package ie.projects.phase6.repository.cart;

import ie.projects.phase6.repository.ConnectionPool;
import ie.projects.phase6.repository.mapper.Mapper;

import java.sql.*;
import java.util.ArrayList;

public class CartMapper extends Mapper<CartDAO, Integer, String> implements ICartMapper {
    private static CartMapper instance;
    private static final String TABLE_NAME = "CART";

    @Override
    protected String getFindAllStatement(String id) {
        return String.format(
                "SELECT * FROM %s WHERE userId = '%s';",
                TABLE_NAME, id);
    }

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
                "CREATE TABLE  %s " +
                        "(id INT PRIMARY KEY, " +
                        "userId VARCHAR(255) NOT NULL, " +
                        "restaurantId VARCHAR(255) NOT NULL);",
                TABLE_NAME);
    }

    @Override
    protected String getFindStatement(Integer id) {
        return String.format("SELECT * FROM %s WHERE id = %d;", TABLE_NAME, id.intValue());
    }

    @Override
    protected String getInsertStatement(CartDAO cart) {
        return String.format(
                "INSERT IGNORE INTO %s VALUES ('%d', '%s', '%s');",
                TABLE_NAME, cart.getCartId(), cart.getUserId(), cart.getRestaurantId());
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

    public CartDAO getCartByUserId(String userId) throws SQLException{

        String sql = String.format("SELECT * FROM %s WHERE userId = '%s';", TABLE_NAME, userId);

        try (Connection con = ConnectionPool.getConnection();
             Statement st = con.createStatement()
        ) {
            ResultSet rs;
            try {
                rs = st.executeQuery(sql);
                if(rs.next())
                    return new CartDAO(rs.getInt("cartId"), rs.getString("userId"), rs.getString("restaurantId"));
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
        return null;
    }

    @Override
    protected String getPreparedInsertStatement(){
        return null;
    }

    @Override
    protected PreparedStatement fillPreparedInsertStatement(PreparedStatement statement, CartDAO user){
        return null;
    }

    @Override
    protected String getDeleteStatement(Integer id) {
        return String.format("DELETE FROM %s WHERE id = %d;", TABLE_NAME, id.intValue());
    }

    @Override
    protected String getDeleteAllStatement(String id){
        return null;
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
