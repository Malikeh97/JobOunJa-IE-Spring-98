package datalayer.datamappers.endorsment;

import datalayer.DBCPDBConnectionPool;
import datalayer.datamappers.Mapper;
import models.Endorsement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
