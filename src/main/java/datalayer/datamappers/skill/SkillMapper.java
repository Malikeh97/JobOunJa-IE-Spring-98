package datalayer.datamappers.skill;

import datalayer.datamappers.Mapper;
import models.Skill;

import java.sql.*;

public class SkillMapper extends Mapper<Skill, String> implements ISkillMapper {
	private static final String TABLE_NAME = "skills";

	public SkillMapper() throws SQLException {
		super(Skill.class, TABLE_NAME);
	}
}
