package ie.projects.phase6.repository.mapper;

import ie.projects.phase6.repository.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;

public abstract class Mapper<T, I, S> implements IMapper<T, I, S> {

    abstract protected String getCreateTableStatement();

    abstract protected String getDeleteTableStatement();

    abstract protected String getFindByIdStatement();

    abstract protected void fillFindByIdStatement(PreparedStatement statement, I id) throws SQLException;

    abstract protected String getFindAllStatement();

    abstract protected void fillFindAllStatement(PreparedStatement statement, S field) throws SQLException;

    abstract protected String getInsertStatement();

    abstract protected void fillInsertStatement(PreparedStatement statement, T obj) throws SQLException;

    abstract protected String getInsertAllStatement();

    abstract protected PreparedStatement fillInsertAllStatement(PreparedStatement statement, T t);

    abstract protected String getDeleteStatement();

    abstract protected void fillDeleteStatement(PreparedStatement statement, I id) throws SQLException;

    abstract protected String getDeleteAllStatement();

    abstract protected void fillDeleteAllStatement(PreparedStatement statement, S field) throws SQLException;

    abstract protected T convertResultSetToObject(ResultSet rs) throws SQLException;

    abstract protected ArrayList<T> convertResultSetToObjects(ResultSet rs) throws SQLException;

    public void createTable() throws SQLException{
        Connection con = ConnectionPool.getConnection();
        Statement statement = con.createStatement();
//        statement.executeUpdate(getDeleteTableStatement());
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

    public T find(I id) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement statement = con.prepareStatement(getFindByIdStatement())
        ) {
            try {
                fillFindByIdStatement(statement, id);
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                return convertResultSetToObject(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }

    public ArrayList<T> findAllById(S field) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement statement = con.prepareStatement(getFindAllStatement())
        ) {
            try {
                fillFindAllStatement(statement, field);
                ResultSet resultSet = statement.executeQuery();
                return convertResultSetToObjects(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findAllByID query.");
                throw ex;
            }
        }
    }

    public void insert(T obj) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement statement = con.prepareStatement(getInsertStatement())
        ) {
            try {
                fillInsertStatement(statement, obj);
                statement.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.insert query.");
                throw ex;
            }
        }
    }

    public void insertAll(ArrayList<T> objs) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement statement = con.prepareStatement(getInsertAllStatement()))
        {
            try {
                con.setAutoCommit(false);
                for (T obj : objs) {
                    fillInsertAllStatement(statement, obj);
                }
                statement.executeBatch();
                con.commit();
            }
            catch (SQLException e1){
                System.out.println("Error in Mapper.insetAll query");
                throw e1;
            }
        }
    }

    public void delete(I id) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement statement = con.prepareStatement(getDeleteStatement())
        ) {
            try {
                fillDeleteStatement(statement, id);
                statement.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.delete query.");
                throw ex;
            }
        }
    }

    public void deleteAll(S field) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement statement = con.prepareStatement(getDeleteAllStatement())
        ) {
            try {
                fillDeleteAllStatement(statement, field);
                statement.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.deleteAll query.");
                throw ex;
            }
        }
    }
}