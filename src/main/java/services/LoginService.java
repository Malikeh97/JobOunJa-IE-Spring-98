package services;

import api.ErrorResponse;
import api.FailResponse;
import api.LoginRequest;
import api.SingleProjectResponse;
import api.data.SingleProjectData;
import datalayer.datamappers.user.UserMapper;
import domain.Bid;
import domain.Project;
import domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static services.SignupService.generateHash;

public class LoginService {
    private UserMapper userMapper = new UserMapper();
    public static final String SALT = "MilkyMocha";

    public LoginService() throws SQLException {
    }

    public String handleLoginRequest(LoginRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        models.User loggedInUser = this.userMapper.findByUsername(request.getUsername());
        if (loggedInUser == null) {
            FailResponse<String> failResponse = new FailResponse<>("username or password is incorrect");
            response.setStatus(400);
            return failResponse.toJSON();
        }
        String saltedPassword = SALT + request.getPassword();
        String hashedPassword = generateHash(saltedPassword);

        if(!hashedPassword.equals(request.getPassword())) {
            FailResponse<String> failResponse = new FailResponse<>("username or password is incorrect");
            response.setStatus(400);
            return failResponse.toJSON();
        }



//        SingleProjectResponse singleProjectResponse = new SingleProjectResponse(new SingleProjectData(project,isBidAdded));
//        return singleProjectResponse.toJSON();
        return null;
    }
}
