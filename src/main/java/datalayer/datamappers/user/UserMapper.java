package datalayer.datamappers.user;

import datalayer.DBCPDBConnectionPool;
import datalayer.datamappers.Mapper;
import models.User;

import java.sql.*;

public class UserMapper extends Mapper<User, Integer> implements IUserMapper {
    private static final String TABLE_NAME = "users";

    public UserMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st = con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " " + "(id INTEGER PRIMARY KEY, first_name TEXT," +
                " last_name TEXT, job_title TEXT, profile_picture_url TEXT, bio TEXT)");
        st.close();
        con.close();

    }

    @Override
    protected Class<User> getMapperClass() {
        return User.class;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
}
