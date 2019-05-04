package datalayer.datamappers.bid;

import datalayer.datamappers.Mapper;
import models.Bid;

import java.sql.SQLException;

public class BidMapper extends Mapper<Bid, String> implements IBidMapper {
	private static final String TABLE_NAME = "bids";

	protected BidMapper() throws SQLException {
		super(Bid.class, TABLE_NAME);
	}
}
