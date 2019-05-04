package utils;

import datalayer.domain.TableColumn;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MapperUtils {
	public static  <T> T convertResultSetToDomainModel(Class<T> clazz, List<TableColumn> columns, ResultSet rs) throws SQLException {
		T newInstance = null;
		try {
			newInstance = clazz.newInstance();
			for (int i = 0; i < columns.size(); i++) {
				TableColumn column = columns.get(i);
				switch (columns.get(i).getType()) {
					case "int":
					case "Integer":
						column.getSetter().invoke(newInstance, rs.getInt(i + 1));
						break;
					case "String":
						column.getSetter().invoke(newInstance, rs.getString(i + 1));
						break;
					case "boolean":
					case "Boolean":
						column.getSetter().invoke(newInstance, rs.getBoolean(i + 1));
						break;
					case "long":
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

	public static String getColumns(List<TableColumn> columns) {
		final StringBuilder strColumns = new StringBuilder();
		columns.forEach(column -> strColumns.append(column.getName()).append(", "));
		strColumns.delete(strColumns.length() - 2, strColumns.length());
		return strColumns.toString();
	}

	public static <T> List<TableColumn> getMapperClassDetails(Class<T> clazz) {
		List<TableColumn> columns = new ArrayList<>();
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
			tableColumn.setName(field.getName());
			Column columnAnnotation = field.getAnnotation(Column.class);
			if (columnAnnotation != null && !columnAnnotation.name().equals(""))
				tableColumn.setName(columnAnnotation.name());
			if (field.isAnnotationPresent(Id.class))
				tableColumn.setIsPrimaryKey(true);
			columns.add(tableColumn);
		}
		return columns;
	}

	public static String createTableSql(String tableName, List<TableColumn> columns) {
		StringBuilder columnsSql = new StringBuilder();
		for(TableColumn column : columns) {
			columnsSql.append(column.getName()).append(" ");
			switch (column.getType()) {
				case "int":
				case "Integer":
				case "Date":
				case "Instant":
				case "boolean":
				case "Boolean":
				case "long":
				case "Long":
					columnsSql.append("INTEGER");
					break;
				case "String":
				case "LocalDate":
					columnsSql.append("TEXT");
					break;
			}
			if (column.getIsPrimaryKey())
				columnsSql.append(" ").append("PRIMARY KEY");
			columnsSql.append(", ");
		}
		columnsSql.delete(columnsSql.length() - 2, columnsSql.length());
		return String.format("CREATE TABLE IF NOT EXISTS %s (%s)", tableName, columnsSql.toString());
	}
}
