package ie.projects.phase7.repository.finalizedCart.undelivered;

import ie.projects.phase7.repository.finalizedCart.FinalizedCartDAO;
import ie.projects.phase7.repository.mapper.IMapper;

import java.sql.SQLException;

public interface IUndeliveredCartMapper extends IMapper<FinalizedCartDAO, Integer, String> {
    void checkState();
    int getMaxId(String deliveredCartTableName) throws SQLException;
}