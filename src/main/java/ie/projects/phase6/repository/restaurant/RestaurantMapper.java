package ie.projects.phase6.repository.restaurant;

import ie.projects.phase6.repository.ConnectionPool;
import ie.projects.phase6.repository.dao.RestaurantDAO;
import ie.projects.phase6.repository.mapper.Mapper;

import java.sql.*;
import java.util.ArrayList;

public class RestaurantMapper extends Mapper<RestaurantDAO, String> implements IRestaurantMapper {

    private static RestaurantMapper instance;
    private static final String TABLE_NAME = "RESTAURANT";

    @Override
    protected String getFindAllStatement(String id) {
        return null;
    }

    private RestaurantMapper() {
    }

    public static RestaurantMapper getInstance(){
        if(instance == null)
            instance = new RestaurantMapper();
        return instance;
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
    protected String getDeleteTableStatement(){
        return "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }

    @Override
    protected String getCreateTableStatement(){
        return String.format(
                "CREATE TABLE  %s " +
                        "(id CHAR(24) NOT NULL PRIMARY KEY, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "logo VARCHAR(255) NOT NULL, " +
                        "locationX FLOAT NOT NULL, " +
                        "locationY FLOAT NOT NULL)",
                TABLE_NAME);
    }

    @Override
    protected String getFindStatement(String id) {
        return "SELECT " + "*" +
                " FROM " + TABLE_NAME +
                " WHERE id = '"+ id + "';";
    }

    @Override
    protected String getInsertStatement(RestaurantDAO restaurant) {
        return null;
    }

    @Override
    protected String getPreparedInsertStatement(){
        return String.format("INSERT IGNORE INTO %s " +
                        "(id, name, logo, locationX, locationY) " +
                        "VALUES(?,?,?,?,?)",
                TABLE_NAME);
    }

    @Override
    protected PreparedStatement fillPreparedInsertStatement(PreparedStatement statement, RestaurantDAO restaurant){
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

    @Override
    protected String getDeleteStatement(String id) {
        return "DELETE FROM " + TABLE_NAME +
                " WHERE id = " + id.toString() + ";";
    }

    @Override
    protected RestaurantDAO convertResultSetToObject(ResultSet rs) throws SQLException {
        return  new RestaurantDAO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getFloat(5));
    }

    @Override
    protected ArrayList<RestaurantDAO> convertResultSetToObjects(ResultSet rs) throws SQLException {
        return  null;
    }

    private String getRestaurantByPagingStatement(int pageNumber, int pageSize){
        return String.format("SELECT *" +
                        "FROM %s " +
                        "LIMIT %d, %d;",
                TABLE_NAME, (pageNumber-1) * pageSize, pageSize);
    }

    @Override
    public ArrayList<RestaurantDAO> getRestaurantsByPaging(int pageNumber, int pageSize){
        ArrayList<RestaurantDAO> fetchedRestaurants = new ArrayList<>();

        try (Connection conn = ConnectionPool.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(getRestaurantByPagingStatement(pageNumber, pageSize));) {
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
