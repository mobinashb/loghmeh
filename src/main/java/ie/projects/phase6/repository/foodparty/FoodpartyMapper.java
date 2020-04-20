package ie.projects.phase6.repository.foodparty;

import ie.projects.phase6.repository.ConnectionPool;
import ie.projects.phase6.repository.food.FoodDAO;
import ie.projects.phase6.repository.mapper.Mapper;
import ie.projects.phase6.repository.order.OrderDAO;

import java.sql.*;
import java.util.ArrayList;

public class FoodpartyMapper extends Mapper<FoodDAO, Object[], String> implements IFoodpartyMapper {

    private static FoodpartyMapper instance;

    private static final String TABLE_NAME = "FOODPARTY";

    private FoodpartyMapper() {

    }

    public static FoodpartyMapper getInstance(){
        if(instance == null)
            instance = new FoodpartyMapper();
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
                        "(restaurantId CHAR(24) NOT NULL, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "count SMALLINT NOT NULL, " +
                        "price FLOAT NOT NULL, " +
                        "PRIMARY KEY (restaurantId,name));",
                TABLE_NAME);
    }

    @Override
    protected String getFindStatement(Object[] id) {
        return  String.format("SELECT * FROM %s WHERE restaurantId = '%s' AND name = '%s';", TABLE_NAME, (String)id[0], (String)id[1]);
    }

    public ArrayList<FoodDAO> getParty(String foodTableName) throws SQLException{
        String sql = String.format("SELECT %s.restaurantId, %s.name, count, %s.price, description, popularity, image, %s.price " +
                "FROM %s " +
                "INNER JOIN %s " +
                "ON %s.restaurantId = %s.restaurantId AND %s.name = %s.name;",
                TABLE_NAME, TABLE_NAME, TABLE_NAME, foodTableName, TABLE_NAME, foodTableName, TABLE_NAME, foodTableName, TABLE_NAME, foodTableName);


        ArrayList<FoodDAO> foods = new ArrayList<>();
        try (Connection con = ConnectionPool.getConnection();
             Statement st = con.createStatement()
        ) {
            ResultSet rs;
            try {
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    foods.add(new FoodDAO(rs.getString(1), rs.getString(2), rs.getString(5), rs.getFloat(6),
                    rs.getString(7), rs.getFloat(4), rs.getInt(3), rs.getFloat(8)));
                }
            } catch (SQLException ex) {
                System.out.println("error in Mapper.getParty query.");
                throw ex;
            }
        }
        return foods;
    }

    public void updateFoodCount(String restaurantId, ArrayList<OrderDAO> orders) throws SQLException{

        String sql = String.format("UPDATE %s SET count = count-? WHERE restaurantId = '%s' AND name = ?;", TABLE_NAME, restaurantId);

        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement statement = con.prepareStatement(sql))
        {
            try {
                con.setAutoCommit(false);
                for (OrderDAO order : orders) {
                    statement.setInt(1, order.getFoodNum());
                    statement.setString(2, order.getFoodName());
                    statement.addBatch();
                }
                statement.executeBatch();
                con.commit();
            }
            catch (SQLException e1){
                System.out.println("Error in inset restaurants");
                throw e1;
            }
        }
    }

    @Override
    protected String getFindAllStatement(String id) {
        return String.format(
                "SELECT * FROM %s WHERE restaurantId = '%s';",
                TABLE_NAME, id);
    }


    @Override
    protected String getInsertStatement(FoodDAO restaurant) {
        return null;
    }

    @Override
    protected String getPreparedInsertStatement(){
        return String.format("REPLACE INTO %s " +
                        "(restaurantId, name, count, price) " +
                        "VALUES(?,?,?,?)",
                TABLE_NAME);
    }

    @Override
    protected PreparedStatement fillPreparedInsertStatement(PreparedStatement statement, FoodDAO food){
        try {
            statement.setString(1, food.getRestaurantId());
            statement.setString(2, food.getName());
            statement.setFloat(3, food.getCount());
            statement.setFloat(4, food.getPrice());
            statement.addBatch();
            return statement;
        }
        catch (SQLException e1){
            System.out.println("Can't add new row to " + TABLE_NAME + " table");
        }
        return null;
    }

    @Override
    protected String getDeleteStatement(Object[] id) {
        return null;
//        return "DELETE FROM " + TABLE_NAME +
//                " WHERE id = " + id.toString() + ";";
    }

    @Override
    protected String getDeleteAllStatement(String id){
        return null;
    }


    @Override
    protected FoodDAO convertResultSetToObject(ResultSet rs) throws SQLException {
        return  new FoodDAO(rs.getString("restaurantId"), rs.getString("name"), rs.getInt("count"), rs.getFloat("price"));
    }

    @Override
    protected ArrayList<FoodDAO> convertResultSetToObjects(ResultSet rs) throws SQLException {
        ArrayList<FoodDAO> foods = new ArrayList<>();
        while (rs.next())
            foods.add(new FoodDAO(rs.getString("restaurantId"), rs.getString("name"), rs.getInt("count"), rs.getFloat("price")));
        return foods;
    }
}
