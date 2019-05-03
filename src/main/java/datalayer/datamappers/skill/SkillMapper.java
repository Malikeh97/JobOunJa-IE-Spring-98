package datalayer.datamappers.skill;

import datalayer.DBCPDBConnectionPool;
import datalayer.datamappers.Mapper;
import models.Skill;

import java.sql.*;

public class SkillMapper extends Mapper<Skill, Integer> implements ISkillMapper {
	private static final String TABLE_NAME = "skills";

	public SkillMapper() throws SQLException {
		Connection con = DBCPDBConnectionPool.getConnection();
		Statement st = con.createStatement();
		st.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id INTEGER PRIMARY KEY, name TEXT)");
		st.close();
		con.close();

	}

	@Override
	protected Class<Skill> getMapperClass() {
		return Skill.class;
	}

	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}
}
