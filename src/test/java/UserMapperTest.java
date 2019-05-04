import datalayer.datamappers.user.IUserMapper;
import datalayer.datamappers.user.UserMapper;
import datalayer.domain.Page;
import datalayer.domain.Sort;
import models.User;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class UserMapperTest {
    private IUserMapper userMapper;

    @Before
    public void setUp() throws SQLException {
        userMapper = new UserMapper();
    }

    @Test
    public void saveUser() throws SQLException{
        User newUser = userMapper.save(new User(UUID.randomUUID().toString(), "Ali", "taba", "bikar", "www.google.com", "delesh pizza pepperoni baghaliashuno mikhad"));
        assertNotNull("user note saved", newUser);
    }

    @Test
    public void deleteUser() throws SQLException {
        userMapper.deleteById(6);
    }

    @Test
    public void findById() throws SQLException {
        User user = userMapper.findById(10);
        assertNotNull("user not found", user);
    }

    @Test
    public void findAll() throws SQLException {
        User newUser = userMapper.save(new User(UUID.randomUUID().toString(), "Malikeh", "taba", "bikar", "www.google.com", "delesh pizza pepperoni baghaliashuno mikhad"));
        newUser = userMapper.save(new User(UUID.randomUUID().toString(), "Ali", "taba", "bikar", "www.google.com", "delesh pizza pepperoni baghaliashuno mikhad"));
        newUser = userMapper.save(new User(UUID.randomUUID().toString(), "Gholi", "taba", "bikar", "www.google.com", "delesh pizza pepperoni baghaliashuno mikhad"));
        System.out.println(userMapper.findAll());
    }

    @Test
    public void findAllSort() throws SQLException {
        System.out.println(userMapper.findAll(new Sort("first_name", Sort.Direction.DESC)));
    }

    @Test
    public void findAllPage() throws SQLException {
//        User newUser = userMapper.save(new User(4, "Mamad", "taba", "bikar", "www.google.com", "delesh pizza pepperoni baghaliashuno mikhad"));
//        newUser = userMapper.save(new User(5, "Reza", "taba", "bikar", "www.google.com", "delesh pizza pepperoni baghaliashuno mikhad"));
//        newUser = userMapper.save(new User(6, "Hamed", "taba", "bikar", "www.google.com", "delesh pizza pepperoni baghaliashuno mikhad"));
        System.out.println(userMapper.findAll(new Page(3, 2, new Sort("id", Sort.Direction.ASC))));
    }
}
