package datalayer.datamappers.endorsment;

import datalayer.DBCPDBConnectionPool;
import datalayer.datamappers.Mapper;
import models.Endorsement;
import utils.MapperUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EndorsementMapper extends Mapper<Endorsement, String> implements IEndorsementMapper {
	private static final String TABLE_NAME = "endorsements";

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

	public List<String> findEndorserIdList(String userSkillId, String endorsedId) throws SQLException {
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

	public static boolean isEndorsedByUserId(String endorserId, String userSkillId) throws SQLException {
		try (Connection con = DBCPDBConnectionPool.getConnection();
			 PreparedStatement st = con.prepareStatement(getFindIsEndorsedByStatement())
		) {
			st.setString(1, String.valueOf(endorserId));
			st.setString(2, String.valueOf(userSkillId));
			ResultSet resultSet = st.executeQuery();
			if (resultSet.next())
				return resultSet.getInt(1) > 0;
			return false;
		}
	}



	private static String getFindIsEndorsedByStatement() {
		return "SELECT " + " count(id) " +
				" FROM " + TABLE_NAME +
				" WHERE " + TABLE_NAME + ".endorser_id = ? " + " and " +
				TABLE_NAME + ".user_skill_id = ?";
	}

	private static String getFindEndorsementListStatement() {
		return "SELECT " +  TABLE_NAME + ".endorser_id"+
				" FROM " + TABLE_NAME +
				" WHERE " + TABLE_NAME + ".endorsed_id = ?" + " and " +
				TABLE_NAME + ".user_skill_id = ?";
	}

}
