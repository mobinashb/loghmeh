package ie.projects.phase6.repository.user;

import ie.projects.phase6.repository.ConnectionPool;
import ie.projects.phase6.repository.mapper.Mapper;

import java.sql.*;
import java.util.ArrayList;

public class UserMapper extends Mapper<UserDAO, String, String> implements IUserMapper {
    private static UserMapper instance;
    private static final String TABLE_NAME = "USER";

    @Override
    protected String getFindAllStatement(String id) {
        return null;
    }

    private UserMapper() {
    }

    public static UserMapper getInstance(){
        if(instance == null)
            instance = new UserMapper();
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
                        "(id CHAR(12) NOT NULL PRIMARY KEY, " +
                        "firstName VARCHAR(255) NOT NULL, " +
                        "lastName VARCHAR(255) NOT NULL, " +
                        "phoneNumber VARCHAR(255) NOT NULL, " +
                        "email VARCHAR(255) NOT NULL, " +
                        "credit FLOAT NOT NULL, " +
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
    protected String getInsertStatement(UserDAO user) {
        return String.format(
                "INSERT INTO %s VALUES ('%s', '%s', '%s', '%s', '%s', %f, %f, %f);",
        TABLE_NAME, user.getId(), user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getEmail(), user.getCredit(), user.getLocationX(), user.getLocationY());
    }

    public void addUserCredit(String userId, float amount) throws SQLException {
        String sql = String.format("UPDATE %s SET credit = credit+%f WHERE id = '%s';", TABLE_NAME, amount, userId);

        Connection con = ConnectionPool.getConnection();
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
        statement.close();
        con.close();
    }

    @Override
    protected String getPreparedInsertStatement(){
        return null;
    }

    @Override
    protected PreparedStatement fillPreparedInsertStatement(PreparedStatement statement, UserDAO user){
        return null;
    }

    @Override
    protected String getDeleteStatement(String id) {
        return null;
    }

    @Override
    protected String getDeleteAllStatement(String userId){
        return null;
    }


    @Override
    protected UserDAO convertResultSetToObject(ResultSet rs) throws SQLException {
        return new UserDAO(rs.getString(1), rs.getString(2),
                rs.getString(3), rs.getString(4), rs.getString(5), rs.getFloat(6), rs.getFloat(7), rs.getFloat(8));
    }

    @Override
    protected ArrayList<UserDAO> convertResultSetToObjects(ResultSet rs) throws SQLException {
        return null;
    }
}
