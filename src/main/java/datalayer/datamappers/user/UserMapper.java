package datalayer.datamappers.user;

import datalayer.DBCPDBConnectionPool;
import datalayer.datamappers.Mapper;
import models.User;

import java.sql.*;

public class UserMapper extends Mapper<User, Integer> implements IUserMapper {
    private static final String TABLE_NAME = "users";
    private static final String COLUMNS = "id, first_name, last_name, job_title, profile_picture_url, bio";

    public UserMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " " + "(id INTEGER PRIMARY KEY, first_name TEXT," +
                " last_name TEXT, job_title TEXT, profile_picture_url TEXT, bio TEXT)");

        st.close();
        con.close();

    }

    @Override
    protected String getColumns() {
        return COLUMNS;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected void setSavePrepareStatement(PreparedStatement st, User user) throws SQLException {
        st.setInt(1, user.getId());
        st.setString(2, user.getFirstName());
        st.setString(3, user.getLastName());
        st.setString(4, user.getJobTitle());
        st.setString(5, user.getProfilePictureURL());
        st.setString(6, user.getBio());
    }

    @Override
    protected User convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6)
        );
    }
}
