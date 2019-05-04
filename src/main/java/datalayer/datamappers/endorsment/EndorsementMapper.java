package datalayer.datamappers.endorsment;

import datalayer.datamappers.Mapper;
import models.Endorsement;

import java.sql.SQLException;

public class EndorsementMapper extends Mapper<Endorsement, String> implements IEndorsementMapper {
	private static final String TABLE_NAME = "projects";

	public EndorsementMapper() throws SQLException {
		super(Endorsement.class, TABLE_NAME);
	}
}
