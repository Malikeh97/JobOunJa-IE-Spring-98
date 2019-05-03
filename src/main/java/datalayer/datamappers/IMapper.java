package datalayer.datamappers;

import datalayer.domain.Page;
import datalayer.domain.Sort;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IMapper<T, ID> {
    T findById(ID id) throws SQLException;
    List<T> findAll() throws SQLException;
    <S extends T> S save(S entity) throws SQLException;
    void deleteById(ID id) throws SQLException;
    List<T> findAll(Sort sort) throws SQLException;
    List<T> findAll(Page page) throws SQLException;
}
