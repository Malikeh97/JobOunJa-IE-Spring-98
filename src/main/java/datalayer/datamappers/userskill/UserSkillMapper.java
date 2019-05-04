package datalayer.datamappers.userskill;

import datalayer.datamappers.Mapper;
import models.UserSkill;

import java.sql.SQLException;

public class UserSkillMapper extends Mapper<UserSkill, String> implements IUserSkillMapper {
	private static final String TABLE_NAME = "user_skills";

	public UserSkillMapper() throws SQLException {
		super(UserSkill.class, TABLE_NAME);
	}
}
