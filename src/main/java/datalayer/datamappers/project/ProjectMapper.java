package datalayer.datamappers.project;

import datalayer.DBCPDBConnectionPool;
import datalayer.datamappers.Mapper;
import models.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectMapper extends Mapper<Project, String> implements IProjectMapper {
	private static final String TABLE_NAME = "projects";

	public ProjectMapper() throws SQLException {
		super(Project.class, TABLE_NAME);
	}

	public Long maxCreationDate() throws SQLException {
		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement(getMaxStatement("creation_date"));
		) {
			ResultSet resultSet = st.executeQuery();
			if (resultSet.next()) {
				return resultSet.getLong(1);
			}
			return null;
		}
	}
}
