package datalayer.datamappers;

import datalayer.DBCPDBConnectionPool;
import datalayer.domain.Page;
import datalayer.domain.Sort;
import datalayer.domain.TableColumn;
import utils.Column;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

public abstract class Mapper<T, ID> implements IMapper<T, ID> {

	private List<TableColumn> columns;

	abstract protected Class<T> getMapperClass();

	abstract protected String getTableName();

	public T findById(ID id) throws SQLException {
		if (columns == null)
			getMapperClassDetails();
		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement(getFindByIdStatement())
		) {
			st.setString(1, String.valueOf(id));
			ResultSet resultSet = st.executeQuery();
			if (resultSet.next())
				return convertResultSetToDomainModel(resultSet);
			return null;
		}
	}

	public T save(T entity) throws SQLException {
		if (columns == null)
			getMapperClassDetails();
		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement(getSaveStatement())
		) {
			for (int i = 0; i < columns.size(); i++)
				st.setString(i + 1, String.valueOf(columns.get(i).getGetter().invoke(entity)));
			int count = st.executeUpdate();
			if (count == 1)
				return entity;
		} catch (IllegalAccessException | InvocationTargetException e) {
			System.out.println("IllegalAccessException | InvocationTargetException");
		}
		return null;
	}


	public void deleteById(ID id) throws SQLException {
		if (columns == null)
			getMapperClassDetails();
		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement(getDeleteByIdStatement());
		) {
			st.setString(1, id.toString());
			st.executeUpdate();
		}
	}

	public List<T> findAll() throws SQLException {
		if (columns == null)
			getMapperClassDetails();
		return findAll(getFindAllStatement());
	}

	public List<T> findAll(Sort sort) throws SQLException {
		if (columns == null)
			getMapperClassDetails();
		String query = String.format("%s ORDER BY %s %s", getFindAllStatement(), sort.getFieldName(), sort.getDirection());
		return findAll(query);
	}

	public List<T> findAll(Page page) throws SQLException {
		if (columns == null)
			getMapperClassDetails();
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
				T newInstance = convertResultSetToDomainModel(resultSet);
				results.add(newInstance);
			}
			return results;
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
		return "INSERT INTO " + "" + getTableName() + " (" + getColumns() + ") " +
				"VALUES (" + new String(new char[columns.size() - 1]).replace("\0", "?, ") + "?)";
	}

	private String getDeleteByIdStatement() {
		return "DELETE " +
				" FROM " + getTableName() +
				" WHERE id = ?";
	}


	private T convertResultSetToDomainModel(ResultSet rs) throws SQLException {
		T newInstance = null;
		try {
			newInstance = getMapperClass().newInstance();
			for (int i = 0; i < columns.size(); i++) {
				TableColumn column = columns.get(i);
				switch (columns.get(i).getType()) {
					case "Integer":
						column.getSetter().invoke(newInstance, rs.getInt(i + 1));
						break;
					case "String":
						column.getSetter().invoke(newInstance, rs.getString(i + 1));
						break;
					case "Boolean":
						column.getSetter().invoke(newInstance, rs.getBoolean(i + 1));
						break;
					case "Long":
						column.getSetter().invoke(newInstance, rs.getLong(i + 1));
						break;
					case "Date":
					case "LocalDate":
					case "Instant":
						column.getSetter().invoke(newInstance, rs.getDate(i + 1));
						break;
				}
			}
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			System.out.println("InstantiationException | IllegalAccessException | InvocationTargetException");
		}
		return newInstance;
	}

	private String getColumns() {
		final StringBuilder strColumns = new StringBuilder();
		columns.forEach(column -> strColumns.append(column.getName()).append(", "));
		strColumns.delete(strColumns.length() - 2, strColumns.length());
		return strColumns.toString();
	}

	private void getMapperClassDetails() {
		columns = new ArrayList<>();
		Class<T> clazz = getMapperClass();
		for (Field field : clazz.getDeclaredFields()) {
			TableColumn tableColumn = new TableColumn();
			tableColumn.setType(field.getType().getSimpleName());
			String fieldName = field.getName();
			fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			String getter = "get" + fieldName;
			String setter = "set" + fieldName;
			try {
				tableColumn.setGetter(clazz.getDeclaredMethod(getter));
				tableColumn.setSetter(clazz.getDeclaredMethod(setter, field.getType()));
			} catch (NoSuchMethodException e) {
				System.out.println(getter + " or " + setter + " not found");
				System.out.println("-- " + e.getLocalizedMessage());
			}
			if (field.isAnnotationPresent(Column.class))
				tableColumn.setName(field.getAnnotation(Column.class).name());
			else
				tableColumn.setName(field.getName());
			columns.add(tableColumn);
		}
	}
}
