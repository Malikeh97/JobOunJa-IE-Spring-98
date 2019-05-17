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
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UsersService usersService = new UsersService();
		SkillRequest skillRequest = parseJSONRequest(request, SkillRequest.class);
		String stringResponse = usersService.handleDeleteRequest(skillRequest, response, request);
		this.sendResponse(stringResponse, response);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] splittedURI = request.getRequestURI().split("/");
		UsersService usersService = new UsersService();
		String stringResponse;
		SkillRequest skillRequest = parseJSONRequest(request, SkillRequest.class);
		if (splittedURI.length == 2) {
			stringResponse = usersService.handleAddSkillRequest(skillRequest, response, request);
		} else if (splittedURI.length == 3) {
			stringResponse = usersService.handleEndorseRequest(skillRequest, response, splittedURI[2], request);
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
		if (splittedURI.length == 2) {
			stringResponse = usersService.handleAllUsersRequest(response, request.getParameter("name"), request);
		} else if (splittedURI.length == 3) {
			stringResponse = usersService.handleSingleUserRequest(response, splittedURI[2]);
		} else {
			ErrorResponse errorResponse = new ErrorResponse(request.getRequestURI() + " not found", 404);
			stringResponse = errorResponse.toJSON();
			response.setStatus(404);
		}
		this.sendResponse(stringResponse, response);
	}
}
