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
                "CREATE TABLE IF NOT EXISTS %s " +
                        "(email VARCHAR(255) NOT NULL PRIMARY KEY, " +
                        "firstName VARCHAR(255), " +
                        "lastName VARCHAR(255), " +
                        "password VARCHAR(255) NOT NULL, " +
                        "credit FLOAT)",
                TABLE_NAME);
    }

    @Override
    protected String getFindStatement(String email) {
        return "SELECT " + "*" +
                " FROM " + TABLE_NAME +
                " WHERE email = '"+ email + "';";
    }

    @Override
    protected String getInsertStatement(UserDAO user) {
        return String.format(
                "INSERT INTO %s (firstName, lastName, email, password) " +
                "values ('%s','%s','%s','%s');",
        TABLE_NAME, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
    }

    public void addUserCredit(String email, float amount) throws SQLException {
        String sql = String.format("UPDATE %s SET credit = credit+%f WHERE email = '%s';", TABLE_NAME, amount, email);

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
        return new UserDAO(rs.getString("firstName"), rs.getString("lastName"),
                rs.getString("password"), rs.getString("email"), rs.getFloat("credit"));
    }

    @Override
    protected ArrayList<UserDAO> convertResultSetToObjects(ResultSet rs) throws SQLException {
        return null;
    }
}
