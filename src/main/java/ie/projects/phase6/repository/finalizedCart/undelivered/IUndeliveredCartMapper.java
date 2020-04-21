package ie.projects.phase6.repository.finalizedCart.undelivered;

import ie.projects.phase6.domain.exceptions.RestaurantNotFound;
import ie.projects.phase6.repository.finalizedCart.FinalizedCartDAO;
import ie.projects.phase6.repository.mapper.IMapper;

import java.sql.SQLException;

public interface IUndeliveredCartMapper extends IMapper<FinalizedCartDAO, Integer, String> {
    void checkState();
}