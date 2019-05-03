package datalayer.datamappers;

import datalayer.DBCPDBConnectionPool;
import datalayer.domain.Page;
import datalayer.domain.Sort;

import java.sql.*;
import java.util.*;

public abstract class Mapper<T, ID> implements IMapper<T, ID> {

    abstract protected String getFindStatement();
    abstract protected String getFindAllStatement();

    abstract protected T convertResultSetToDomainModel(ResultSet rs) throws SQLException;


    public T findById(ID id) throws SQLException {

        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindStatement())
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
//    <S extends T> S save(S entity) throws SQLException;
//    void deleteById(ID id) throws SQLException;
//    List<T> findAll(Sort sort) throws SQLException;
//    List<T> findAll(Page page) throws SQLException;
}
