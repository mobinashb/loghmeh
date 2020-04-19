package ie.projects.phase6.repository.order;

import ie.projects.phase6.domain.exceptions.CartValidationException;
import ie.projects.phase6.repository.mapper.IMapper;

import java.sql.SQLException;

public interface IOrderMapper extends IMapper<OrderDAO, Object[], Integer> {

    void updateFoodNum(OrderDAO orderDAO) throws SQLException, CartValidationException;
}