import datalayer.datamappers.user.IUserMapper;
import datalayer.datamappers.user.UserMapper;
import models.User;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertNull;

public class UserMapperTest {
    private IUserMapper userMapper;

    @Before
    public void setUp() throws SQLException {
        userMapper = new UserMapper();
    }

    @Test
    public void saveUser() throws SQLException{
        User newUser = userMapper.save(new User(1, "Ali", "taba", "bikar", "www.google.com", "delesh pizza pepperoni baghaliashuno mikhad"));
        assertNull("user note saved", newUser);
    }
}
