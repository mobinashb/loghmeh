package ie.projects.phase6.repository.mapper;

import ie.projects.phase6.repository.ConnectionPool;
import ie.projects.phase6.repository.dao.RestaurantDAO;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IMapper<T, I> {
    void createTable() throws SQLException;
    T find(I id) throws SQLException;
    ArrayList<T> findAllById(I id) throws SQLException;
    void insert(T t) throws SQLException;
    void delete(I id) throws SQLException;
    void insertAll(ArrayList<T> objs) throws SQLException;
}
