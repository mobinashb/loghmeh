package ie.projects.phase6.repository.foodparty;

import ie.projects.phase6.repository.ConnectionPool;
import ie.projects.phase6.repository.dao.FoodpartyDAO;
import ie.projects.phase6.repository.dao.RestaurantDAO;
import ie.projects.phase6.repository.mapper.Mapper;

import java.sql.*;
import java.util.ArrayList;

public class FoodpartyMapper extends Mapper<FoodpartyDAO, String> implements IFoodpartyMapper {

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
                "CREATE TABLE  %s " +
                        "(restaurantId CHAR(24) NOT NULL, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "count SMALLINT NOT NULL, " +
                        "oldPrice FLOAT NOT NULL, " +
                        "PRIMARY KEY (restaurantID,name))",
                TABLE_NAME);
    }



    @Override
    protected String getFindStatement(String id) {
        return "ali";
//        return "SELECT " + COLUMNS +
//                " FROM " + TABLE_NAME +
//                " WHERE id = "+ id.toString() + ";";
    }

    public ArrayList<FoodpartyDAO> getParty() throws SQLException{
        String sql = "SELECT * FROM " + TABLE_NAME + ";";
        ArrayList<FoodpartyDAO> foods = new ArrayList<>();
        try (Connection con = ConnectionPool.getConnection();
             Statement st = con.createStatement()
        ) {
            ResultSet rs;
            try {
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    foods.add(new FoodpartyDAO(rs.getString(1), rs.getString(2),
                            rs.getFloat(3), rs.getFloat(4)));
                }
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findAllByID query.");
                throw ex;
            }
        }
        return foods;
    }

    @Override
    protected String getFindAllStatement(String id) {
        return String.format(
                "SELECT * FROM %s WHERE %s.restaurantId = '%s';",
                TABLE_NAME, TABLE_NAME, id);
    }


    @Override
    protected String getInsertStatement(FoodpartyDAO restaurant) {
        return "ali";
//        return "INSERT INTO " + TABLE_NAME +
//                "(" + COLUMNS + ")" + " VALUES "+
//                "("+
//                example.getId().toString() + "," +
//                '"' + example.getText() + '"' +
//                ");";
    }

    @Override
    protected String getPreparedInsertStatement(){
        return String.format("REPLACE INTO %s " +
                        "(restaurantId, name, count, oldPrice) " +
                        "VALUES(?,?,?,?)",
                TABLE_NAME);
    }

    @Override
    protected PreparedStatement fillPreparedInsertStatement(PreparedStatement statement, FoodpartyDAO food){
        try {
            statement.setString(1, food.getRestaurantId());
            statement.setString(2, food.getName());
            statement.setFloat(3, food.getCount());
            statement.setFloat(4, food.getOldPrice());
            statement.addBatch();
            return statement;
        }
        catch (SQLException e1){
            System.out.println("Can't add new row to " + TABLE_NAME + " table");
        }
        return null;
    }

    @Override
    protected String getDeleteStatement(String id) {
        return "DELETE FROM " + TABLE_NAME +
                " WHERE id = " + id.toString() + ";";
    }

    @Override
    protected FoodpartyDAO convertResultSetToObject(ResultSet rs) throws SQLException {
        return  null;//new FoodpartyDAO("a", "b", "c", 12, "a", 12);
//                rs.getInt(1),
//                rs.getString(2)
    }

    @Override
    protected ArrayList<FoodpartyDAO> convertResultSetToObjects(ResultSet rs) throws SQLException {
        ArrayList<FoodpartyDAO> foods = new ArrayList<>();
//        while (rs.next()) {
//            foods.add(new FoodpartyDAO(rs.getString(1), rs.getString(2),
//                    rs.getString(3), rs.getFloat(4), rs.getString(5), rs.getFloat(6)));
//        }
        return foods;
    }
}
