package datalayer.datamappers.user;

import datalayer.datamappers.Mapper;
import models.User;

import java.sql.*;

public class UserMapper extends Mapper<User, Integer> implements IUserMapper {
    private static final String TABLE_NAME = "users";

    public UserMapper() throws SQLException {
        super(User.class, TABLE_NAME);
    }
}
