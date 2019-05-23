package datalayer.datamappers.endorsment;

import datalayer.DBCPDBConnectionPool;
import datalayer.datamappers.Mapper;
import datalayer.datamappers.user.UserMapper;
import models.Endorsement;
import utils.MapperUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EndorsementMapper extends Mapper<Endorsement, String> implements IEndorsementMapper {
	public static final String TABLE_NAME = "endorsements";

	public EndorsementMapper() throws SQLException {
		super(Endorsement.class, TABLE_NAME);
	}

	public Integer countNumOfEndorsements(String userSkillId, String endorsedId) throws SQLException {
		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement(getCountAllStatement() +
					 " WHERE endorsements.endorsed_id IS '" + endorsedId +
					 "' and endorsements.user_skill_id = '" + userSkillId + "'");
		) {
			ResultSet resultSet = st.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
			return null;
		}
	}

	public List<String> findEndorsersList(String userSkillId, String endorsedId) throws SQLException {
		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement(getFindEndorsementListStatement()
			 )) {
			List<String> endorserIdList = new ArrayList<>();
			st.setString(1, endorsedId);
			st.setString(2, userSkillId);
			ResultSet resultSet = st.executeQuery();
			while (resultSet.next()) {
				endorserIdList.add(resultSet.getString(1));
			}
			return endorserIdList;
		}
	}

	public static boolean isEndorsedByUserId(String endorserId, String userSkillId, String endorsedId) throws SQLException {
		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement(getFindIsEndorsedByStatement())
		) {
			st.setString(1, String.valueOf(endorserId));
			st.setString(2, String.valueOf(userSkillId));
			st.setString(3, String.valueOf(endorsedId));
			ResultSet resultSet = st.executeQuery();
			if (resultSet.next())
				return resultSet.getInt(1) > 0;
			return false;
		}
	}



	private static String getFindIsEndorsedByStatement() {
		return "SELECT " + " count(id) " +
				" FROM " + TABLE_NAME +
				" WHERE endorser_id = ? and " +
				"user_skill_id = ? and " +
				"endorsed_id = ?";
	}

	private static String getFindEndorsementListStatement() {
		return String.format("SELECT u.user_name"+
				" FROM %s e" +
				" JOIN %s u ON e.endorser_id = u.id" +
				" WHERE e.endorsed_id = ? and e.user_skill_id = ?",
						TABLE_NAME,
				UserMapper.TABLE_NAME);
	}

}
