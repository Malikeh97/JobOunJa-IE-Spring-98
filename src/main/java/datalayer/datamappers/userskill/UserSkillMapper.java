package datalayer.datamappers.userskill;

import datalayer.DBCPDBConnectionPool;
import datalayer.datamappers.Mapper;
import models.Skill;
import models.UserSkill;
import utils.MapperUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserSkillMapper extends Mapper<UserSkill, String> implements IUserSkillMapper {
	private static final String TABLE_NAME = "user_skills";

	public UserSkillMapper() throws SQLException {
		super(UserSkill.class, TABLE_NAME);
	}

	public HashMap<String, List<Skill>> findUserSkills() throws SQLException {
		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement(getTrippleInnerJoinStatement("skills.id, skills.name, user_skills.user_id",
					 "users",
					 "skills",
					 " users.id = user_skills.user_id",
					 "skills.id = user_skills.skill_id"
					));
		) {
			ResultSet resultSet = st.executeQuery();
			HashMap<String, List<Skill>> userSkillList = new HashMap<>();
			while (resultSet.next()) {
				Skill newSkill = new Skill();
				newSkill.setId(resultSet.getString(1));
				newSkill.setName(resultSet.getString(2));
				if(!userSkillList.containsKey(resultSet.getString(3)))
					userSkillList.put(resultSet.getString(3), new ArrayList<>());
				userSkillList.get(resultSet.getString(3)).add(newSkill) ;
			}
			return userSkillList;
		}
	}


}
