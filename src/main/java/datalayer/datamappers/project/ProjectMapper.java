package datalayer.datamappers.project;

import datalayer.datamappers.Mapper;
import models.Project;

import java.sql.SQLException;

public class ProjectMapper extends Mapper<Project, String> implements IProjectMapper {
	private static final String TABLE_NAME = "projects";

	public ProjectMapper() throws SQLException {
		super(Project.class, TABLE_NAME);
	}
}
