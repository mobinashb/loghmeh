package ie.projects.phase6.repository.food;

import ie.projects.phase6.repository.mapper.Mapper;

import java.sql.*;
import java.util.ArrayList;

public class FoodMapper extends Mapper<FoodDAO, Object[], String> implements IFoodMapper {

    private static FoodMapper instance;

    private static final String TABLE_NAME = "FOOD";

    private FoodMapper() {
    }

    public static FoodMapper getInstance(){
        if(instance == null)
            instance = new FoodMapper();
        return instance;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getDeleteTableStatement(){
        return "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }

    @Override
    protected String getCreateTableStatement(){
        return String.format(
                "CREATE TABLE IF NOT EXISTS %s " +
                        "(restaurantId CHAR(24) NOT NULL, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "description VARCHAR(255) NOT NULL, " +
                        "popularity FLOAT NOT NULL, " +
                        "image VARCHAR(255) NOT NULL, " +
                        "price FLOAT NOT NULL, " +
                        "PRIMARY KEY (restaurantId,name))",
                TABLE_NAME);
    }

    @Override
    protected String getFindByIdStatement()
    {
        return String.format("SELECT * FROM %s WHERE restaurantId = ? AND name = ?;", TABLE_NAME);
    }

    @Override
    protected void fillFindByIdStatement(PreparedStatement statement, Object[] id) throws SQLException{
        statement.setString(1, (String) id[0]);
        statement.setString(2, (String) id[1]);

    }

    @Override
    protected String getFindAllStatement() {
        return String.format(
                "SELECT * FROM %s WHERE %s.restaurantId = ?;",
                TABLE_NAME, TABLE_NAME);
    }

    @Override
    protected void fillFindAllStatement(PreparedStatement statement, String field) throws SQLException{
        statement.setString(1, field);
    }


    @Override
    protected String getInsertStatement() {
        return null;
    }

    @Override
    protected void fillInsertStatement(PreparedStatement statement, FoodDAO food) throws SQLException{
        return;
    }

    @Override
    protected String getInsertAllStatement(){
        return String.format("INSERT IGNORE INTO %s " +
                        "(restaurantId, name, description, popularity, image, price) " +
                        "VALUES(?,?,?,?,?,?)",
                TABLE_NAME);
    }

    @Override
    protected PreparedStatement fillInsertAllStatement(PreparedStatement statement, FoodDAO food){
        try {
            statement.setString(1, food.getRestaurantId());
            statement.setString(2, food.getName());
            statement.setString(3, food.getDescription());
            statement.setFloat(4, food.getPopularity());
            statement.setString(5, food.getImage());
            statement.setFloat(6, food.getPrice());
            statement.addBatch();
            return statement;
        }
        catch (SQLException e1){
            System.out.println("Can't add new rpw to " + TABLE_NAME + " table");
        }
        return null;
    }

    @Override
    protected String getDeleteStatement() {
        return null;
    }

    @Override
    protected void fillDeleteStatement(PreparedStatement statement, Object[] id){return;}

    @Override
    protected String getDeleteAllStatement(){
        return null;
    }

    @Override
    protected void fillDeleteAllStatement(PreparedStatement statement, String field) throws SQLException{
        return;
    }


    @Override
    protected FoodDAO convertResultSetToObject(ResultSet rs) throws SQLException {
        return  new FoodDAO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getString(5), rs.getFloat(6));
    }

    @Override
    protected ArrayList<FoodDAO> convertResultSetToObjects(ResultSet rs) throws SQLException {
        ArrayList<FoodDAO> foods = new ArrayList<>();
        while (rs.next()) {
            foods.add(new FoodDAO(rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getFloat(4), rs.getString(5), rs.getFloat(6)));
        }
        return foods;
    }
}
