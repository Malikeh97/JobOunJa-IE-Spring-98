package datalayer.datamappers.projectskill;

import datalayer.datamappers.Mapper;
import models.ProjectSkill;

import java.sql.SQLException;

public class ProjectSkillMapper extends Mapper<ProjectSkill, String> implements IProjectSkillMapper {
	public static final String TABLE_NAME = "project_skills";

	public ProjectSkillMapper() throws SQLException {
		super(ProjectSkill.class, TABLE_NAME);
	}
}
