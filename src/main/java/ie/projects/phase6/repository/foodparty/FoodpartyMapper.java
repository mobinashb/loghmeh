package ie.projects.phase6.repository.foodparty;

import ie.projects.phase6.repository.ConnectionPool;
import ie.projects.phase6.repository.food.FoodDAO;
import ie.projects.phase6.repository.mapper.Mapper;
import java.sql.*;
import java.util.ArrayList;

public class FoodpartyMapper extends Mapper<FoodDAO, Object[]> implements IFoodpartyMapper {

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

    public void updateFoodCount(Object[] id, int foodNum) throws SQLException{
        String sql = String.format("UPDATE %s SET count -= %d WHERE restaurantId = '%s' AND name = '%s';" +
                TABLE_NAME, foodNum, (String)id[0], (String)id[1]);

        try (Connection con = ConnectionPool.getConnection();
             Statement st = con.createStatement()
        ) {
            try {
                st.executeQuery(sql);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.getParty query.");
                throw ex;
            }
        }
    }

    @Override
    protected String getFindAllStatement(Object[] id) {
        return null;
//        return String.format(
//                "SELECT * FROM %s WHERE %s.restaurantId = '%s';",
//                TABLE_NAME, TABLE_NAME, id[0]);
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
    protected FoodDAO convertResultSetToObject(ResultSet rs) throws SQLException {
        return  new FoodDAO(rs.getString("restaurantId"), rs.getString("name"), rs.getInt("count"), rs.getFloat("price"));
    }

    @Override
    protected ArrayList<FoodDAO> convertResultSetToObjects(ResultSet rs) throws SQLException {
        return null;
    }
}
