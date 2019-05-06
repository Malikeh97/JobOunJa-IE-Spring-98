package datalayer.datamappers.user;

import datalayer.datamappers.IMapper;
import models.User;

import java.sql.SQLException;

public interface IUserMapper extends IMapper<User, String> {
	public domain.User findByIdWithSkills(String id) throws SQLException;
}
