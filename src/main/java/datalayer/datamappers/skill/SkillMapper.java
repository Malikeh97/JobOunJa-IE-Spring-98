package datalayer.datamappers.skill;

import datalayer.DBCPDBConnectionPool;
import datalayer.datamappers.Mapper;
import models.Skill;
import models.UserSkill;
import utils.MapperUtils;

import java.sql.*;

public class SkillMapper extends Mapper<Skill, String> implements ISkillMapper {
	private static final String TABLE_NAME = "skills";

	public SkillMapper() throws SQLException {
		super(Skill.class, TABLE_NAME);
	}
	public static Skill findByName(String name) throws SQLException {
		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement(getFindByNameStatement())
		) {
			st.setString(1, String.valueOf(name));
			ResultSet resultSet = st.executeQuery();
			if (resultSet.next())
				return MapperUtils.convertResultSetToDomainModel(Skill.class, columns, resultSet);
			return null;
		}
	}

	private String getFindByNameStatement() {
		return "SELECT " + MapperUtils.getColumns(columns) +
				" FROM " + TABLE_NAME +
				" WHERE name = ?";
	}
}
