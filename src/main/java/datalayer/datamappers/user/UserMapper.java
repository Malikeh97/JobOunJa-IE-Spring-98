package datalayer.datamappers.user;

import datalayer.DBCPDBConnectionPool;
import datalayer.datamappers.Mapper;
import models.User;
import utils.MapperUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserMapper extends Mapper<User, String> implements IUserMapper {
    private static final String TABLE_NAME = "users";

    public UserMapper() throws SQLException {
        super(User.class, TABLE_NAME);
    }

    public List<User> findNameLike(String nameLike) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindAllStatement() +
                     " WHERE first_name || last_name LIKE '%?%'")
        ) {
            st.setString(1, nameLike);
            List<User> userList = new ArrayList<>();
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next())
                userList.add(MapperUtils.convertResultSetToDomainModel(User.class, columns, resultSet));
            return userList;
        }
    }

}
