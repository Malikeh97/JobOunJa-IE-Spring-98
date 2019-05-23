package datalayer.datamappers.userskill;

import datalayer.DBCPDBConnectionPool;
import datalayer.datamappers.Mapper;
import datalayer.datamappers.endorsment.EndorsementMapper;
import models.Skill;
import models.UserSkill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserSkillMapper extends Mapper<UserSkill, String> implements IUserSkillMapper {
	public static final String TABLE_NAME = "user_skills";

	public UserSkillMapper() throws SQLException {
		super(UserSkill.class, TABLE_NAME);
	}

	public List<Skill> findUserSkillById(String userId) throws SQLException {
		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement(getTrippleInnerJoinStatement("skills.id, skills.name",
					 "users",
					 "skills",
					 " users.id = user_skills.user_id ",
					 " skills.id = user_skills.skill_id ") + " WHERE user_skills.user_id = ?" )
			) {
			st.setString(1, userId);
			ResultSet resultSet = st.executeQuery();
			List<Skill> skills = new ArrayList<>();
			while (resultSet.next()) {
				Skill newSkill = new Skill();
				newSkill.setId(resultSet.getString(1));
				newSkill.setName(resultSet.getString(2));
				skills.add(newSkill);
			}
			return skills;
		}
	}

	public List<Skill> findSkillNotOwnedById(String userId) throws SQLException {
		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement("SELECT skills.id, skills.name " +
					 " FROM skills " +
					 "WHERE skills.id NOT IN (" +
					 getTrippleInnerJoinStatement("skills.id",
					 "users",
					 "skills",
					 " users.id = user_skills.user_id ",
					 " skills.id = user_skills.skill_id ") + " WHERE user_skills.user_id = ?)" )
		) {
			st.setString(1,userId);
			ResultSet resultSet = st.executeQuery();
			List<Skill> skills = new ArrayList<>();
			while (resultSet.next()) {
				Skill newSkill = new Skill();
				newSkill.setId(resultSet.getString(1));
				newSkill.setName(resultSet.getString(2));
				skills.add(newSkill);
			}
			return skills;
		}
	}

	public void delete(String userId, String skillId) throws SQLException {
		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement(getDeleteEndorsementByUserIdAndUserSkillIdStatement())
		) {
			st.setString(1, userId);
			st.setString(2, skillId);
			System.out.println(st.executeUpdate());
		}

		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement(getDeleteByUserIdAndSkillId())
		) {
			st.setString(1, userId);
			st.setString(2, skillId);
			System.out.println(st.executeUpdate());
		}
	}

	private String getDeleteByUserIdAndSkillId() {
		return String.format("DELETE" +
						" FROM %s" +
						" WHERE user_id = ? and skill_id = ?",
				TABLE_NAME
		);
	}

	private String getDeleteEndorsementByUserIdAndUserSkillIdStatement() {
		return String.format("DELETE" +
				" FROM %s" +
				" WHERE endorsed_id = ? and user_skill_id = ?",
				EndorsementMapper.TABLE_NAME
		);
	}


}
