package ie.projects.phase6.repository.mapper;

import ie.projects.phase6.repository.ConnectionPool;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;

public abstract class Mapper<T, I, S> implements IMapper<T, I, S> {

    abstract protected String getDeleteTableStatement();

    abstract protected String getCreateTableStatement();

    abstract protected String getFindStatement(I id);

    abstract protected String getFindAllStatement(S field);

    abstract protected String getInsertStatement(T t);

    abstract protected String getPreparedInsertStatement();

    abstract protected PreparedStatement fillPreparedInsertStatement(PreparedStatement statement, T t);

    abstract protected String getDeleteStatement(I id);

    abstract protected String getDeleteAllStatement(S filed);

    abstract protected T convertResultSetToObject(ResultSet rs) throws SQLException;

    abstract protected ArrayList<T> convertResultSetToObjects(ResultSet rs) throws SQLException;

    public T find(I id) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             Statement st = con.createStatement()
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery(getFindStatement(id));
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

    public ArrayList<T> findAllById(S field) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             Statement st = con.createStatement()
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery(getFindAllStatement(field));
                return convertResultSetToObjects(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findAllByID query.");
                throw ex;
            }
        }
    }

    public void insert(T obj) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             Statement st = con.createStatement()
        ) {
            try {
                st.executeUpdate(getInsertStatement(obj));
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
             Statement st = con.createStatement()
        ) {
            try {
                st.executeUpdate(getDeleteStatement(id));
            } catch (SQLException ex) {
                System.out.println("error in Mapper.delete query.");
                throw ex;
            }
        }
    }

    public void deleteAll(S field) throws SQLException {
        try (Connection con = ConnectionPool.getConnection();
             Statement st = con.createStatement()
        ) {
            try {
                st.executeUpdate(getDeleteAllStatement(field));
            } catch (SQLException ex) {
                System.out.println("error in Mapper.deleteAll query.");
                throw ex;
            }
        }
    }
}