package datalayer.datamappers.userskill;

import datalayer.DBCPDBConnectionPool;
import datalayer.datamappers.Mapper;
import models.UserSkill;
import utils.MapperUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSkillMapper extends Mapper<UserSkill, String> implements IUserSkillMapper {
	private static final String TABLE_NAME = "user_skills";

	public UserSkillMapper() throws SQLException {
		super(UserSkill.class, TABLE_NAME);
	}


}
