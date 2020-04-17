package ie.projects.phase6.repository.user;

import ie.projects.phase6.repository.dao.UserDao;

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
        UserDao user = new UserDao("123456789123", "احسان", "خامس‌پناه", "09121111111", "ehsan.kp@gmail.com", 100000, 11, 1);
        mapper.insert(user);
    }

    public UserDao findUser(String id) throws SQLException{
        return mapper.find(id);
    }

    public void addCredit(String id, float amount) throws SQLException{
        this.mapper.addUserCredit(id, amount);
    }

}
