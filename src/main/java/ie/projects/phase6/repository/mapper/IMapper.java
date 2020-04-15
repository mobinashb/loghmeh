package ie.projects.phase6.repository.mapper;

import ie.projects.phase6.repository.ConnectionPool;
import ie.projects.phase6.repository.dao.RestaurantDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IMapper<T, I> {
    T find(I id) throws SQLException;
    void insert(T t) throws SQLException;
    void delete(I id) throws SQLException;
    void insertAll(ArrayList<T> objs) throws SQLException;
}
