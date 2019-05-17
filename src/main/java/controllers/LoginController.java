package controllers;

import api.ErrorResponse;
import api.FailResponse;
import api.LoginRequest;
import services.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login")
public class LoginController extends BaseController {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String stringResponse;
		try {
			LoginService loginService = new LoginService();
			LoginRequest loginRequest = parseJSONRequest(request, LoginRequest.class);
			Map<String, String> errors = validateLogin(loginRequest);
			if (!errors.isEmpty()) {
				FailResponse<Map<String, String>> failResponse = new FailResponse<>(errors);
				stringResponse = failResponse.toJSON();
				response.setStatus(400);
			} else
				stringResponse = loginService.handleLoginRequest(loginRequest, response);
		} catch (SQLException | InvalidKeySpecException | NoSuchAlgorithmException e) {
			ErrorResponse errorResponse = new ErrorResponse("Internal server error", 500);
			stringResponse = errorResponse.toJSON();
			response.setStatus(500);
		}
		this.sendResponse(stringResponse, response);
	}

	private Map<String, String> validateLogin(LoginRequest loginRequest) {
		HashMap<String, String> errors = new HashMap<>();
		if (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty())
			errors.put("username", "username is required");
		if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty())
			errors.put("password", "password is required");

		return errors;
	}
}
