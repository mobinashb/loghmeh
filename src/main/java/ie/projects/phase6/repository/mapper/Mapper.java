package ie.projects.phase6.repository.mapper;

import ie.projects.phase6.repository.ConnectionPool;
import ie.projects.phase6.repository.dao.RestaurantDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Mapper<T, I> implements IMapper<T, I> {

    abstract protected String getDeleteTableStatement();

    abstract protected String getCreateTableStatement();

    abstract protected String getFindStatement(I id);

    abstract protected String getFindAllStatement(I id);

    abstract protected String getInsertStatement(T t);

    abstract protected String getPreparedInsertStatement();

    abstract protected PreparedStatement fillPreparedInsertStatement(PreparedStatement statement, T t);

    abstract protected String getDeleteStatement(I id);

    abstract protected T convertResultSetToObject(ResultSet rs) throws SQLException;

    abstract protected ArrayList<T> convertResultSetToObjects(ResultSet rs) throws SQLException;

    public T find(I id) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindStatement(id))
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                resultSet.next();
                return convertResultSetToObject(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }

    public void createTable() throws SQLException{
        Connection con = ConnectionPool.getConnection();
        Statement statement = con.createStatement();
        statement.executeUpdate(getDeleteTableStatement());
        statement.executeUpdate(getCreateTableStatement());
        statement.close();
        con.close();
    }

    public void deleteTable() throws SQLException{
        Connection con = ConnectionPool.getConnection();
        Statement statement = con.createStatement();
        statement.executeUpdate(getDeleteTableStatement());
        statement.close();
        con.close();
    }

    public ArrayList<T> findAllById(I id) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindAllStatement(id))
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                return convertResultSetToObjects(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findAllByID query.");
                throw ex;
            }
        }
    }

    public void insert(T obj) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getInsertStatement(obj))
        ) {
            try {
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.insert query.");
                throw ex;
            }
        }
    }

    public void insertAll(ArrayList<T> objs) throws SQLException {
        String sql = getPreparedInsertStatement();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement statement = con.prepareStatement(sql))
        {
            try {
                con.setAutoCommit(false);
                for (T obj : objs) {
                    fillPreparedInsertStatement(statement, obj);
                }
                statement.executeBatch();
                con.commit();
            }
            catch (SQLException e1){
                System.out.println("Error in inset restaurants");
                throw e1;
            }
        }
    }

    public void delete(I id) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getDeleteStatement(id))
        ) {
            try {
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.delete query.");
                throw ex;
            }
        }
    }
}