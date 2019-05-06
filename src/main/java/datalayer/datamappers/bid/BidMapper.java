package datalayer.datamappers.bid;

import datalayer.datamappers.Mapper;
import models.Bid;

import java.sql.SQLException;

public class BidMapper extends Mapper<Bid, String> implements IBidMapper {
	public static final String TABLE_NAME = "bids";

	public BidMapper() throws SQLException {
		super(Bid.class, TABLE_NAME);
	}
}
