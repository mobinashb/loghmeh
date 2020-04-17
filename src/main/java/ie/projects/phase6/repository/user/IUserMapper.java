package ie.projects.phase6.repository.user;

import ie.projects.phase6.repository.dao.FoodDAO;
import ie.projects.phase6.repository.dao.UserDao;
import ie.projects.phase6.repository.mapper.IMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IUserMapper extends IMapper<UserDao, String> {
    void addUserCredit(String userId, float amount) throws SQLException;
}
