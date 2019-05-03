package datalayer.datamappers;

import datalayer.DBCPDBConnectionPool;
import datalayer.domain.Page;
import datalayer.domain.Sort;

import java.sql.*;
import java.util.*;

public abstract class Mapper<T, ID> implements IMapper<T, ID> {

    private Map<String, String> columns = new HashMap<>();

    abstract protected String getColumns();
    abstract protected String getTableName();

    abstract protected T convertResultSetToDomainModel(ResultSet rs) throws SQLException;

    public T findById(ID id) throws SQLException {

        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindByIdStatement())
        ) {
            st.setString(1, id.toString());
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                if(resultSet.next())
                    return convertResultSetToDomainModel(resultSet);
                return null;
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findById query.");
                throw ex;
            }
        }
    }

    public List<T> findAll() throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindAllStatement())
        ) {
            ResultSet resultSet;
            try {
                List<T> results = new ArrayList<T>();
                resultSet = st.executeQuery();
                while(resultSet.next()) {
                    T newInstance = convertResultSetToDomainModel(resultSet);
                    results.add(newInstance);
                }
                return results;

            } catch (SQLException ex) {
                System.out.println("error in Mapper.findAll query.");
                throw ex;
            }
        }
    }

    public <S extends T> S save(S entity) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getSaveStatement())
        ) {
            try {
                int count = st.executeUpdate();
                if (count == 1)
                    return entity;
                return null;
            } catch (SQLException ex) {
                System.out.println("error in Mapper.save query.");
                throw ex;
            }
        }
    }

    public void deleteById(ID id) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getDeleteByIdStatement());
        ) {
            st.setString(1, id.toString());
            try {
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.deleteById query.");
                throw ex;
            }
        }
    }
    public List<T> findAll(Sort sort) throws SQLException {
        String query = String.format("%s ORDER BY %s %s", getFindAllStatement(), sort.getFieldName(), sort.getDirection());
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(query)
        ) {
            ResultSet resultSet;
            try {
                List<T> results = new ArrayList<T>();
                resultSet = st.executeQuery();
                while(resultSet.next()) {
                    T newInstance = convertResultSetToDomainModel(resultSet);
                    results.add(newInstance);
                }
                return results;

            } catch (SQLException ex) {
                System.out.println("error in Mapper.findAll(sort) query.");
                throw ex;
            }
        }
    }
    public List<T> findAll(Page page) throws SQLException {
        String query = String.format("%s ORDER BY %s %s LIMIT %d OFFSET %d",
                getFindAllStatement(),
                page.getSort().getFieldName(),
                page.getSort().getDirection(),
                page.getLimit(),
                page.getOffset()
                );
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(query)
        ) {
            ResultSet resultSet;
            try {
                List<T> results = new ArrayList<T>();
                resultSet = st.executeQuery();
                while(resultSet.next()) {
                    T newInstance = convertResultSetToDomainModel(resultSet);
                    results.add(newInstance);
                }
                return results;
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findAll(page) query.");
                throw ex;
            }
        }
    }

    private String getFindByIdStatement() {
        return "SELECT " + getColumns() +
                " FROM " + getTableName() +
                " WHERE id = ?";
    }

    private String getFindAllStatement() {
        return "SELECT " + getColumns() +
                " FROM " + getTableName();
    }

    private String getSaveStatement() {
        int numOfColumns = getColumns().split(",").length;
        return "INSERT INTO " + "" + getTableName() + " (" + getColumns() + ") " +
                "VALUES (" + new String(new char[numOfColumns - 1]).replace("\0", "?, ")  + "?)";
    }

    private String getDeleteByIdStatement() {
        return  "DELETE " +
                " FROM " + getTableName() +
                " WHERE id = ?";
    }
}
