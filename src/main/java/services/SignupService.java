package services;

import api.ErrorResponse;
import api.SignupRequest;
import api.SignupResponse;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import datalayer.datamappers.user.UserMapper;
import models.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.UUID;


public class SignupService {

    private UserMapper userMapper = new UserMapper();
    public static final String SALT = "MilkyMocha";

    public SignupService() throws SQLException {
    }

    public String handleSignupRequest(SignupRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        try {

            System.out.println(request.toString());
            User newUser = new User();
            newUser.setId(UUID.randomUUID().toString());
            String hashed = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
            newUser.setPassword(hashed);
            newUser.setUserName(request.getUsername());
            newUser.setFirstName(request.getName());
            newUser.setLastName(request.getFamilyName());
            newUser.setJobTitle(request.getJobTitle());
            newUser.setProfilePictureURL(request.getImage());
            newUser.setBio(request.getBio());

            if (this.userMapper.save(newUser) == null) {
                ErrorResponse errorResponse = new ErrorResponse("Internal server error", 500);
                response.setStatus(500);
                return errorResponse.toJSON();
            }
            SignupResponse signupResponse = new SignupResponse("You registered successfully");
            return signupResponse.toJSON();
        } catch (SQLException e) {
            if (e instanceof MySQLIntegrityConstraintViolationException) {
                ErrorResponse errorResponse = new ErrorResponse("This username already exists", 1505);
                response.setStatus(403);
                return errorResponse.toJSON();
            }
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", 500);
            response.setStatus(500);
            return errorResponse.toJSON();

        }
    }

    public static String generateHash(String input) {
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };
            for (int idx = 0; idx < hashedBytes.length; ++idx) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            // handle error here.
        }

        return hash.toString();
    }

}

