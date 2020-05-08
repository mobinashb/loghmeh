package ie.projects.phase7.repository.restaurant;

import ie.projects.phase7.configs.DatabaseTablesName;
import ie.projects.phase7.repository.ConnectionPool;
import ie.projects.phase7.repository.mapper.Mapper;

import java.sql.*;
import java.util.ArrayList;

public class RestaurantMapper extends Mapper<RestaurantDAO, String, String> implements IRestaurantMapper {

    private static RestaurantMapper instance;
    private static final String TABLE_NAME = DatabaseTablesName.RESTAURANT_TABLE;

    private RestaurantMapper() {
    }

    public static RestaurantMapper getInstance(){
        if(instance == null)
            instance = new RestaurantMapper();
        return instance;
    }

    @Override
    protected String getCreateTableStatement(){
        return String.format(
                "CREATE TABLE IF NOT EXISTS %s " +
                        "(id CHAR(24) NOT NULL PRIMARY KEY, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "logo VARCHAR(255) NOT NULL, " +
                        "locationX FLOAT NOT NULL, " +
                        "locationY FLOAT NOT NULL)",
                TABLE_NAME);
    }

    @Override
    protected String getDeleteTableStatement(){
        return "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }

    @Override
    protected String getFindByIdStatement() {
        return "SELECT " + "*" +
                " FROM " + TABLE_NAME +
                " WHERE id = ?;";
    }

    @Override
    protected void fillFindByIdStatement(PreparedStatement statement, String id) throws SQLException{
        statement.setString(1, id);
    }

    @Override
    protected String getFindAllStatement() {
        return null;
    }

    @Override
    protected void fillFindAllStatement(PreparedStatement statement, String field) throws SQLException{
        return;
    }

    @Override
    protected String getInsertStatement() {
        return null;
    }

    @Override
    protected void fillInsertStatement(PreparedStatement statement, RestaurantDAO restaurant) throws SQLException{
        return;
    }

    @Override
    protected String getInsertAllStatement(){
        return String.format("INSERT IGNORE INTO %s " +
                        "(id, name, logo, locationX, locationY) " +
                        "VALUES(?,?,?,?,?)",
                TABLE_NAME);
    }

    @Override
    protected PreparedStatement fillInsertAllStatement(PreparedStatement statement, RestaurantDAO restaurant){
        try {
            statement.setString(1, restaurant.getId());
            statement.setString(2, restaurant.getName());
            statement.setString(3, restaurant.getLogo());
            statement.setFloat(4, restaurant.getLocationX());
            statement.setFloat(5, restaurant.getLocationY());
            statement.addBatch();
            return statement;
        }
        catch (SQLException e1){
            System.out.println("Can't add new row to " + TABLE_NAME + " table");
        }
        return null;
    }

    public ArrayList<String> getRestaurantsNameById(ArrayList<String> restaurantsId) throws SQLException{

        ArrayList<String> restaurantsName = new ArrayList<>();
        String sql = String.format("SELECT name FROM %s WHERE id = (?)", TABLE_NAME);

        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement statement = con.prepareStatement(sql))
        {
            try {
                for(int i=0; i < restaurantsId.size(); i++) {
                    statement.setString(1, restaurantsId.get(i));
                    ResultSet rs = statement.executeQuery();
                    while (rs.next())
                        restaurantsName.add(rs.getString(1));
                }
            }
            catch (SQLException e1){
                System.out.println("Error in find names by id");
                throw e1;
            }
        }
        return restaurantsName;
    }

    @Override
    protected String getDeleteStatement() {
        return "DELETE FROM " + TABLE_NAME +
                " WHERE id = ?;";
    }

    @Override
    protected void fillDeleteStatement(PreparedStatement statement, String id) throws SQLException{
        statement.setString(1, id);
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
    protected RestaurantDAO convertResultSetToObject(ResultSet rs) throws SQLException {
        return  new RestaurantDAO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getFloat(5));
    }

    @Override
    protected ArrayList<RestaurantDAO> convertResultSetToObjects(ResultSet rs) throws SQLException {
        return  null;
    }

    private String getRestaurantByPagingStatement(){
        return String.format("SELECT *" +
                        "FROM %s " +
                        "LIMIT ?, ?;",
                TABLE_NAME);
    }

    private void fillRestaurantByPagingStatement(PreparedStatement statement, int pageNumber, int pageSize) throws SQLException{
        statement.setInt(1, (pageNumber-1) * pageSize);
        statement.setInt(2, pageSize);
    }

    public ArrayList<RestaurantDAO> getRestaurantsByPaging(int pageNumber, int pageSize){
        ArrayList<RestaurantDAO> fetchedRestaurants = new ArrayList<>();

        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(getRestaurantByPagingStatement())
             ) {
            fillRestaurantByPagingStatement(stmt, pageNumber, pageSize);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                fetchedRestaurants.add(new RestaurantDAO(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getFloat(4), rs.getFloat(5)));
            }
        } catch (SQLException e) {
            System.out.println("Can't get restaurants from wanted page");
            e.printStackTrace();
        }
        return fetchedRestaurants;
    }

    public ArrayList<RestaurantDAO> searchRestaurants(String restaurantName, String foodName, String foodTableName, int pageNumber, int pageSize) throws SQLException{
        String sql;
        if(restaurantName == null) {
             sql = String.format("SELECT %s.* FROM %s, %s WHERE %s.id = %s.restaurantId AND %s.name = %s LIMIT %d, %d;"
                    , TABLE_NAME, TABLE_NAME, foodTableName, TABLE_NAME, foodTableName, foodTableName, foodName, (pageNumber-1) * pageSize, pageSize);
        }
        else if(foodName == null) {
            sql = String.format("SELECT * FROM %s WHERE %s.name = %s LIMIT %d, %d;"
                    , TABLE_NAME, TABLE_NAME, restaurantName, (pageNumber-1) * pageSize, pageSize);
        }
        else {
            sql = String.format("SELECT %s.* FROM %s, %s WHERE %s.id = %s.restaurantId AND %s.name = %s AND %s.name = %s LIMIT %d, %d;"
                    , TABLE_NAME, TABLE_NAME, foodTableName, TABLE_NAME, foodTableName, foodTableName, foodName, TABLE_NAME, restaurantName, (pageNumber-1) * pageSize, pageSize);
        }

        ArrayList<RestaurantDAO> fetchedRestaurants = new ArrayList<>();

        try (Connection conn = ConnectionPool.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                fetchedRestaurants.add(new RestaurantDAO(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getFloat(4), rs.getFloat(5)));
            }
        } catch (SQLException e) {
            System.out.println("Can't get restaurants from wanted page");
            e.printStackTrace();
        }
        return fetchedRestaurants;
    }
}
