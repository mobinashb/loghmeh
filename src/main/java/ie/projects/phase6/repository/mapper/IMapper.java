package ie.projects.phase6.repository.mapper;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IMapper<T, I, S> {
    void createTable() throws SQLException;
    void deleteTable() throws SQLException;
    T find(I id) throws SQLException;
    ArrayList<T> findAllById(S field) throws SQLException;
    void insert(T t) throws SQLException;
    void delete(I id) throws SQLException;
    void insertAll(ArrayList<T> objs) throws SQLException;
}
