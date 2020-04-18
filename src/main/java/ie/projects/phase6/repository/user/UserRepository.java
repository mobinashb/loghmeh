package ie.projects.phase6.repository.user;

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

    public void insertTempUser() throws SQLException{
        UserDAO user = new UserDAO("123456789123", "احسان", "خامس‌پناه", "09121111111", "ehsan.kp@gmail.com", 100000, 11, 1);
        mapper.insert(user);
    }

    public UserDAO findUser(String id) throws SQLException{
        return mapper.find(id);
    }

    public void addCredit(String id, float amount) throws SQLException{
        this.mapper.addUserCredit(id, amount);
    }

}
