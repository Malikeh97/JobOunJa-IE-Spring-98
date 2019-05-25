package datalayer.datamappers.user;

import datalayer.DBCPDBConnectionPool;
import datalayer.datamappers.Mapper;
import datalayer.datamappers.endorsment.EndorsementMapper;
import datalayer.datamappers.skill.SkillMapper;
import datalayer.datamappers.userskill.UserSkillMapper;
import models.User;
import utils.MapperUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserMapper extends Mapper<User, String> implements IUserMapper {
	public static final String TABLE_NAME = "users";

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
			st.setString(2, null);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				convertWithSkills(user, rs);
			}
		}
		return user;
	}

	public domain.User findByNameWithSkills(String name) throws SQLException {
		domain.User user = new domain.User();
		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement(getUserWithSkillsStatement())
		) {
			st.setString(1, null);
			st.setString(2, name);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				convertWithSkills(user, rs);
			}
		}
		return user;
	}

	private void convertWithSkills(domain.User user, ResultSet rs) throws SQLException {
		EndorsementMapper endorsementMapper = new EndorsementMapper();
		user.setId(rs.getString(1));
		user.setFirstName(rs.getString(2));
		user.setLastName(rs.getString(3));
		user.setJobTitle(rs.getString(4));
		user.setProfilePictureURL(rs.getString(5));
		user.setBio(rs.getString(6));
		user.setUsername(rs.getString(9));
		user.setSkills(new ArrayList<>());
		do {
			String skillId = rs.getString(7);
			if (skillId == null)
				break;
			domain.Skill skill = new domain.Skill();
			skill.setId(skillId);
			skill.setName(rs.getString(8));
			skill.setPoint(endorsementMapper.countNumOfEndorsements(skill.getId(), user.getId()));
			user.getSkills().add(skill);
		} while (rs.next());
	}

	private String getUserWithSkillsStatement() {
		return String.format("SELECT u.id, u.first_name, u.last_name, u.job_title, u.profile_picture_url, u.bio, " +
						"s.id as skill_id, s.name, u.user_name " +
						"FROM %s u " +
						"LEFT JOIN %s us ON u.id = us.user_id " +
						"LEFT JOIN %s s ON s.id = us.skill_id " +
						"where u.id = ? or u.user_name = ?",
				UserMapper.TABLE_NAME,
				UserSkillMapper.TABLE_NAME,
				SkillMapper.TABLE_NAME
		);
	}

    public List<User> findNameLike(String nameLike) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindAllStatement() +
                     " WHERE CONCAT_WS(\" \", first_name, last_name) LIKE ?")
        ) {
            st.setString(1, "%" + nameLike + "%");
            List<User> userList = new ArrayList<>();
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next())
                userList.add(MapperUtils.convertResultSetToDomainModel(User.class, columns, resultSet));
            return userList;
        }
    }

	public User findByUsername(String username) throws SQLException {
		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement(getFindByUsernameStatement())) {
			st.setString(1, username);
			ResultSet resultSet = st.executeQuery();
			if (resultSet.next())
				return MapperUtils.convertResultSetToDomainModel(User.class, columns, resultSet);
			return null;
		}
	}

	private String getFindByUsernameStatement() {
		return " SELECT " + MapperUtils.getColumns(columns) +
				" From users "  +
				" WHERE user_name = ?";
	}

}
