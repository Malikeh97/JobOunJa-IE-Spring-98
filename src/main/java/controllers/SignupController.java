package controllers;

import api.ErrorResponse;
import api.FailResponse;
import api.SignupRequest;
import org.codehaus.jackson.map.ObjectMapper;
import services.SignupService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/sign-up")
public class SignupController extends BaseController {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] splittedURI = request.getRequestURI().split("/");
        String stringResponse;

        try {
            SignupService signupService = new SignupService();

            if (splittedURI.length == 3) {
                ObjectMapper mapper = new ObjectMapper();
                System.out.println("Req: " + request);
                SignupRequest signupRequest = parseJSONRequest(request, SignupRequest.class);
                Map<String, String> errors =  validateSignUp (signupRequest);
                if(!errors.isEmpty()) {
                    FailResponse<Map<String, String>> failResponse = new FailResponse<>( errors);
                    stringResponse = failResponse.toJSON();
                    response.setStatus(400);
                }
                else
                    stringResponse = signupService.handleSignupRequest(signupRequest, response);
            } else {
                ErrorResponse errorResponse = new ErrorResponse(request.getRequestURI() + " not found", 404);
                stringResponse = errorResponse.toJSON();
                response.setStatus(404);
            }

        } catch (SQLException e) {
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", 500);
            stringResponse = errorResponse.toJSON();
            response.setStatus(500);
        }

        this.sendResponse(stringResponse, response);
    }

    private Map<String, String> validateSignUp (SignupRequest signupRequest) {
        HashMap<String, String> errors = new HashMap<>();
        if(signupRequest.getName() == null || signupRequest.getName().isEmpty())
            errors.put("name", "name is required");
        if(signupRequest.getFamilyName() == null || signupRequest.getFamilyName().isEmpty())
            errors.put("familyName", "familyName is required");
        if(signupRequest.getUsername() == null || signupRequest.getUsername().isEmpty())
            errors.put("username", "username is required");
        if(signupRequest.getPassword() == null || signupRequest.getPassword().isEmpty())
            errors.put("password", "password is required");
        else if( signupRequest.getPassword().length() < 8)
            errors.put("password", "password must be longer than 8 characters");
        if(signupRequest.getConfirmPassword() == null || signupRequest.getConfirmPassword().isEmpty())
            errors.put("confirmPassword", "confirmPassword is required");
        else if (!signupRequest.getConfirmPassword().equals(signupRequest.getPassword()))
            errors.put("confirmPassword", "password and confirmPassword must be equal");
        if(signupRequest.getJobTitle() == null || signupRequest.getJobTitle().isEmpty())
            errors.put("jobTitle", "jobTitle is required");
        if(signupRequest.getImage() == null || signupRequest.getImage().isEmpty())
            errors.put("image", "image is required");
        if(signupRequest.getBio() == null || signupRequest.getBio().isEmpty())
            errors.put("bio", "bio is required");
        return errors;
    }

}




