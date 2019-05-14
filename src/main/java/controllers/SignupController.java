package controllers;

import api.ErrorResponse;
import api.SignupRequest;
import org.codehaus.jackson.map.ObjectMapper;
import services.SignupService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/sign-up")
public class SignupController extends BaseController {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] splittedURI = request.getRequestURI().split("/");
        SignupService signupService = new SignupService();
        String stringResponse = null;

        if (splittedURI.length == 3) {
            ObjectMapper mapper = new ObjectMapper();
            SignupRequest signupRequest = parseJSONRequest(request, SignupRequest.class);

            try {
                stringResponse = signupService.handleSignupRequest(signupRequest, response);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            ErrorResponse errorResponse = new ErrorResponse(request.getRequestURI() + " not found", 404);
            stringResponse = errorResponse.toJSON();
            response.setStatus(404);
        }

        this.sendResponse(stringResponse, response);
    }

}


