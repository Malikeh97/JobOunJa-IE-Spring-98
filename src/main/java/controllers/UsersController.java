package controllers;

import api.SkillRequest;
import api.ErrorResponse;
import services.UsersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/*")
public class UsersController extends BaseController {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] splittedURI = request.getRequestURI().split("/");
		UsersService usersService = new UsersService();
		String stringResponse;
        if (splittedURI.length == 5) {
			SkillRequest skillRequest = parseJSONRequest(request, SkillRequest.class);
			switch (splittedURI[4]) {
				case "add_skill":
					stringResponse = usersService.handleAddSkillRequest(skillRequest, response, splittedURI[3]);
					break;
				case "endorse":
					stringResponse = usersService.handleEndorseRequest(skillRequest, response, splittedURI[3]);
					break;
				case "delete_skill":
					stringResponse = usersService.handleDeleteRequest(skillRequest, response, splittedURI[3]);
					break;
				default:
					ErrorResponse errorResponse = new ErrorResponse(request.getRequestURI() + " not found", 404);
					stringResponse = errorResponse.toJSON();
					response.setStatus(404);
					break;
			}
		} else {
			ErrorResponse errorResponse = new ErrorResponse(request.getRequestURI() + " not found", 404);
			stringResponse = errorResponse.toJSON();
			response.setStatus(404);
        }
		this.sendResponse(stringResponse, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] splittedURI = request.getRequestURI().split("/");
		UsersService usersService = new UsersService();
		String stringResponse;
		if (splittedURI.length == 3) {
			stringResponse = usersService.handleAllUsersRequest(response);
		} else if (splittedURI.length == 4) {
			stringResponse = usersService.handleSingleUserRequest(response, splittedURI[3]);
		} else {
			ErrorResponse errorResponse = new ErrorResponse(request.getRequestURI() + " not found", 404);
			stringResponse = errorResponse.toJSON();
			response.setStatus(404);
		}
		this.sendResponse(stringResponse, response);
	}
}
