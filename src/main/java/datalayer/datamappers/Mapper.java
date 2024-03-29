package datalayer.datamappers;

import datalayer.DBCPDBConnectionPool;
import datalayer.domain.Page;
import datalayer.domain.Sort;
import datalayer.domain.TableColumn;
import utils.Column;
import utils.MapperUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

public abstract class Mapper<T, ID> implements IMapper<T, ID> {

	private Class<T> clazz;
	private String tableName;
	protected List<TableColumn> columns;

	protected Mapper(Class<T> clazz, String tableName) throws SQLException {
		this.clazz = clazz;
		this.tableName = tableName;
		this.columns = MapperUtils.getMapperClassDetails(clazz);
		try (Connection con = DBCPDBConnectionPool.getConnection();
			 Statement st = con.createStatement()
		) {
			String createTableSQL = MapperUtils.createTableSql(tableName, this.columns);
			st.executeUpdate(createTableSQL);
		}
	}

	public T findById(ID id) throws SQLException {
		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement(getFindByIdStatement())
		) {
			st.setString(1, String.valueOf(id));
			ResultSet resultSet = st.executeQuery();
			if (resultSet.next())
				return MapperUtils.convertResultSetToDomainModel(this.clazz, columns, resultSet);
			return null;
		}
	}

	public T save(T entity) throws SQLException {
		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement(getSaveStatement())
		) {
			for (int i = 0; i < columns.size(); i++) {
				Object obj = columns.get(i).getGetter().invoke(entity);
				st.setObject(i + 1, obj);
			}
			int count = st.executeUpdate();
			if (count == 1)
				return entity;
		} catch (IllegalAccessException | InvocationTargetException e) {
			System.out.println("IllegalAccessException | InvocationTargetException");
		}
		return null;
	}


	public void deleteById(ID id) throws SQLException {
		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement(getDeleteByIdStatement())
		) {
			st.setString(1, id.toString());
			st.executeUpdate();
		}
	}

	public List<T> findAll() throws SQLException {
		return findAll(getFindAllStatement());
	}

	public List<T> findAll(Sort sort) throws SQLException {
		String query = String.format("%s ORDER BY %s %s", getFindAllStatement(), sort.getFieldName(), sort.getDirection());
		return findAll(query);
	}

	public List<T> findAll(Page page) throws SQLException {
		String query = String.format("%s ORDER BY %s %s LIMIT %d OFFSET %d",
				getFindAllStatement(),
				page.getSort().getFieldName(),
				page.getSort().getDirection(),
				page.getLimit(),
				page.getOffset()
		);
		return findAll(query);
	}

	private List<T> findAll(String query) throws SQLException {
		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement(query)
		) {
			List<T> results = new ArrayList<>();
			ResultSet resultSet = st.executeQuery();
			while (resultSet.next()) {
				T newInstance = MapperUtils.convertResultSetToDomainModel(this.clazz, columns, resultSet);
				results.add(newInstance);
			}
			return results;
		}
	}

	public Integer countAll() throws SQLException {
		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement(getCountAllStatement());
		) {
			ResultSet resultSet = st.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
			return null;
		}
	}


	private String getFindByIdStatement() {
		return "SELECT " + MapperUtils.getColumns(columns) +
				" FROM " + this.tableName +
				" WHERE id = ?";
	}

	protected String getFindAllStatement() {
		return "SELECT " + MapperUtils.getColumns(columns) +
				" FROM " + this.tableName;
	}

	private String getSaveStatement() {
		return "INSERT INTO " + "" + this.tableName + " (" + MapperUtils.getColumns(columns) + ") " +
				"VALUES (" + new String(new char[columns.size() - 1]).replace("\0", "?, ") + "?)";
	}

	protected String getDeleteByIdStatement() {
		return "DELETE " +
				" FROM " + this.tableName +
				" WHERE id = ?";
	}

	protected String getCountAllStatement() {
		return "SELECT COUNT(id) " +
				" FROM " + this.tableName;
	}

	protected String getMaxStatement(String columnName) {
		return "SELECT Max(" + columnName + ") " +
				" FROM " + this.tableName;
	}

	protected String getTrippleInnerJoinStatement(String columns, String tableName1, String tableName2, String onFirst, String onSecond) {
		return " SELECT " + columns +
				" From " + this.tableName +
				" LEFT JOIN " + tableName1 + " ON " + onFirst +
				" LEFT JOIN " + tableName2 + " ON " + onSecond ;
	}

}
