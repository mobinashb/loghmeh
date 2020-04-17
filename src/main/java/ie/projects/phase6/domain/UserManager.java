package ie.projects.phase6.domain;


import ie.projects.phase6.domain.exceptions.NegativeCreditAmount;
import ie.projects.phase6.repository.dao.UserDao;
import ie.projects.phase6.repository.user.UserRepository;
import ie.projects.phase6.utilities.JsonStringCreator;

import java.sql.SQLException;

public class UserManager {
    private static UserManager instance;

    private UserRepository userRepository;

    private UserManager() throws SQLException {
        this.userRepository = UserRepository.getInstance();
        this.userRepository.insertTempUser();
    }

    public static UserManager getInstance() throws SQLException {
        if (instance == null)
            instance = new UserManager();
        return instance;
    }

    public UserDao getUserById(String id) throws SQLException{
        return this.userRepository.findUser(id);
    }

    public void addCredit(String userId, float amount) throws NegativeCreditAmount, SQLException{
        if(amount <= 0)
            throw new NegativeCreditAmount(JsonStringCreator.msgCreator("برای افزایش اعتبار مقدار مثبتی را وارد نمایید"));
        this.userRepository.addCredit(userId, amount);
    }
}
