package loghmeh.repository.user;

import loghmeh.configs.DatabaseTablesName;
import loghmeh.repository.ConnectionPool;
import loghmeh.repository.mapper.Mapper;

import java.sql.*;
import java.util.ArrayList;

public class UserMapper extends Mapper<UserDAO, String, String> implements IUserMapper {
    private static UserMapper instance;
    private static final String TABLE_NAME = DatabaseTablesName.USER_TABLE;

    private UserMapper() {
    }

    public static UserMapper getInstance(){
        if(instance == null)
            instance = new UserMapper();
        return instance;
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
    protected String getDeleteTableStatement(){
        return "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }

    @Override
    protected String getFindByIdStatement() {
        return "SELECT " + "*" +
                " FROM " + TABLE_NAME +
                " WHERE email = ?;";
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
        return String.format(
                "INSERT INTO %s (firstName, lastName, email, password, credit) " +
                "values (?,?,?,?,?);",
        TABLE_NAME);
    }

    @Override
    protected void fillInsertStatement(PreparedStatement statement, UserDAO user) throws SQLException{
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getPassword());
        statement.setFloat(5, user.getCredit());
    }

    public void addUserCredit(String email, float amount) throws SQLException {
        String sql = String.format("UPDATE %s SET credit = credit+%f WHERE email = '%s';", TABLE_NAME, amount, email);

        Connection con = ConnectionPool.getConnection();
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
        statement.close();
        con.close();
    }

    public boolean validateUser(String email, String password) throws SQLException{
        String sql = String.format("SELECT * FROM %s WHERE email = ? AND password = ?", TABLE_NAME);
        boolean userValidated = false;
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement statement = con.prepareStatement(sql))
        {
            try {
                statement.setString(1, email);
                statement.setString(2, password);
                ResultSet rs = statement.executeQuery();
                if(rs.next())   userValidated = true;
                rs.close();
            }
            catch (SQLException e1){
                System.out.println("Error in validate user");
                e1.printStackTrace();
                throw e1;
            }
        }
        return userValidated;
    }

    @Override
    protected String getInsertAllStatement(){
        return null;
    }

    @Override
    protected PreparedStatement fillInsertAllStatement(PreparedStatement statement, UserDAO user){
        return null;
    }

    @Override
    protected String getDeleteStatement() {
        return null;
    }

    @Override
    protected void fillDeleteStatement(PreparedStatement statement, String id){return;}

    @Override
    protected String getDeleteAllStatement(){
        return null;
    }

    @Override
    protected void fillDeleteAllStatement(PreparedStatement statement, String field) throws SQLException{
        return;
    }


    @Override
    protected UserDAO convertResultSetToObject(ResultSet rs) throws SQLException {
        return new UserDAO(rs.getString("firstName"), rs.getString("lastName"),
                rs.getString("email"), rs.getString("password"), rs.getFloat("credit"));
    }

    @Override
    protected ArrayList<UserDAO> convertResultSetToObjects(ResultSet rs) throws SQLException {
        return null;
    }
}
