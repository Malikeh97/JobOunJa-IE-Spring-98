package datalayer.datamappers.user;

import datalayer.DBCPDBConnectionPool;
import datalayer.datamappers.Mapper;
import datalayer.datamappers.endorsment.EndorsementMapper;
import datalayer.datamappers.skill.SkillMapper;
import datalayer.datamappers.userskill.UserSkillMapper;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserMapper extends Mapper<User, String> implements IUserMapper {
	public static final String TABLE_NAME = "users";
	private EndorsementMapper endorsementMapper = new EndorsementMapper();

	public UserMapper() throws SQLException {
		super(User.class, TABLE_NAME);
	}


	@Override
	public domain.User findByIdWithSkills(String id) throws SQLException {
		domain.User user = new domain.User();
		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement(getUserWithSkillsStatement())
		) {
			st.setString(1, id);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				convertWithSkills(user, rs);
			}
		}
		return user;
	}

	private void convertWithSkills(domain.User user, ResultSet rs) throws SQLException {
		user.setId(rs.getString(1));
		user.setFirstName(rs.getString(2));
		user.setLastName(rs.getString(3));
		user.setJobTitle(rs.getString(4));
		user.setProfilePictureURL(rs.getString(5));
		user.setBio(rs.getString(6));
		user.setSkills(new ArrayList<>());
		do {
			String skillId = rs.getString(7);
			if (skillId == null)
				break;
			domain.Skill skill = new domain.Skill();
			skill.setId(skillId);
			skill.setName(rs.getString(8));
			skill.setPoint(this.endorsementMapper.countNumOfEndorsements(skill.getId(), user.getId()));
			user.getSkills().add(skill);
		} while (rs.next());
	}

	private String getUserWithSkillsStatement() {
		return String.format("SELECT u.id, u.first_name, u.last_name, u.job_title, u.profile_picture_url, u.bio, " +
						"s.id as skill_id, s.name " +
						"FROM %s u " +
						"LEFT JOIN %s us ON u.id = us.user_id " +
						"LEFT JOIN %s s ON s.id = us.skill_id " +
						"where u.id = ?",
				UserMapper.TABLE_NAME,
				UserSkillMapper.TABLE_NAME,
				SkillMapper.TABLE_NAME
		);
	}
}
