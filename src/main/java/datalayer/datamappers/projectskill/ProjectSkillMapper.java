package datalayer.datamappers.projectskill;

import datalayer.datamappers.Mapper;
import models.ProjectSkill;

import java.sql.SQLException;

public class ProjectSkillMapper extends Mapper<ProjectSkill, String> implements IProjectSkillMapper {
	private static final String TABLE_NAME = "user_skills";

	protected ProjectSkillMapper() throws SQLException {
		super(ProjectSkill.class, TABLE_NAME);
	}
}
