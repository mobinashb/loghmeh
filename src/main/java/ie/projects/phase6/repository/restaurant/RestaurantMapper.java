package ie.projects.phase6.repository.restaurant;

import ie.projects.phase6.domain.core.Restaurant;
import ie.projects.phase6.repository.ConnectionPool;
import ie.projects.phase6.repository.dao.RestaurantDAO;
import ie.projects.phase6.repository.mapper.Mapper;

import java.sql.*;
import java.util.ArrayList;

public class RestaurantMapper extends Mapper<RestaurantDAO, String> implements IRestaurantMapper {

    private static RestaurantMapper instance;

    private static final String TABLE_NAME = "RESTAURANT";

    private RestaurantMapper() throws SQLException {
        Connection con = ConnectionPool.getConnection();
        Statement st = con.createStatement();
        st.executeUpdate(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
        st.executeUpdate(String.format(
                "CREATE TABLE  %s " +
                        "(id CHAR(24) NOT NULL PRIMARY KEY, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "logo VARCHAR(255) NOT NULL, " +
                        "locationX DOUBLE NOT NULL, " +
                        "locationY DOUBLE NOT NULL)",
                TABLE_NAME));
        st.close();
        con.close();
    }

    public static RestaurantMapper getInstance() throws SQLException{
        if(instance == null)
            instance = new RestaurantMapper();
        return instance;
    }

    @Override
    protected String getFindStatement(String id) {
        return "ali";
//        return "SELECT " + COLUMNS +
//                " FROM " + TABLE_NAME +
//                " WHERE id = "+ id.toString() + ";";
    }

    @Override
    protected String getInsertStatement(RestaurantDAO restaurant) {
        return "ali";
//        return "INSERT INTO " + TABLE_NAME +
//                "(" + COLUMNS + ")" + " VALUES "+
//                "("+
//                example.getId().toString() + "," +
//                '"' + example.getText() + '"' +
//                ");";
    }

    @Override
    protected String getPreparedInsertStatement(RestaurantDAO restaurant){
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
            statement.setDouble(4, restaurant.getLocationX());
            statement.setDouble(5, restaurant.getLocationY());
            statement.addBatch();
            return statement;
        }
        catch (SQLException e1){
            System.out.println("Can't add new rpw to " + TABLE_NAME + " table");
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
        return  new RestaurantDAO("a", "b", "c", 12, 13);
//                rs.getInt(1),
//                rs.getString(2)
    }

    @Override
    public ArrayList<RestaurantDAO> getContainsText(String text) throws SQLException {
        ArrayList<RestaurantDAO> result = new ArrayList<RestaurantDAO>();
        String statement = "SELECT " + "COLUMNS" + " FROM " + TABLE_NAME +
                " Where text LIKE " + "'%" + text + "%'";
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(statement);
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                while (resultSet.next())
                    result.add(convertResultSetToObject(resultSet));
                return result;
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }

//    private void addRestaurant(RestaurantDAO restaurant, PreparedStatement statement) throws SQLException{
////        statement.setString(1, restaurant.getId());
////        statement.setString(2, restaurant.getName());
////        statement.setString(3, restaurant.getLogo());
////        statement.setDouble(4, restaurant.getLocationX());
////        statement.setDouble(5, restaurant.getLocationY());
////        statement.addBatch();
//    }

}
