package loghmeh.repository.finalizedCart.undelivered;

import loghmeh.repository.finalizedCart.FinalizedCartDAO;
import loghmeh.repository.mapper.IMapper;

import java.sql.SQLException;

public interface IUndeliveredCartMapper extends IMapper<FinalizedCartDAO, Integer, String> {
    void checkState();
    int getMaxId(String deliveredCartTableName) throws SQLException;
}