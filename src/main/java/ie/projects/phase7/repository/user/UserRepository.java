package ie.projects.phase7.repository.user;

import ie.projects.phase7.domain.exceptions.DuplicateEmail;
import ie.projects.phase7.utilities.JsonStringCreator;

import java.sql.SQLException;

public class UserRepository {

    private static UserRepository instance;
    private IUserMapper mapper;

    private UserRepository() throws SQLException
    {
        mapper = UserMapper.getInstance();
        mapper.createTable();
    }

    public static UserRepository getInstance() throws SQLException{
        if (instance == null)
            instance = new UserRepository();
        return instance;
    }

    public void insertUser(String firstName, String lastName, String email, String password) throws DuplicateEmail{
        try {
            UserDAO user = new UserDAO(firstName, lastName, email, password, 0);
            mapper.insert(user);
        }
        catch (SQLException e1){
            throw new DuplicateEmail(JsonStringCreator.msgCreator("کاربری با ایمیل وارد شده قبلا در سایت ثبت نام کرده‌است"));
        }
    }

    public boolean validateUser(String email, String password){
        try {
            return mapper.validateUser(email, password);
        }
        catch (SQLException e1){
            return false;
        }
    }

    public UserDAO findUser(String id) throws SQLException{
        return mapper.find(id);
    }

    public void updateUserCredit(String id, float amount) throws SQLException{
        this.mapper.addUserCredit(id, amount);
    }

}
