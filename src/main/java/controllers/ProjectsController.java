package controllers;

import api.AddBidRequest;
import api.ErrorResponse;
import api.FailResponse;
import org.codehaus.jackson.map.ObjectMapper;
import services.ProjectsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/projects/*")
public class ProjectsController extends BaseController {
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] splittedURI = request.getRequestURI().split("/");
		String stringResponse;
		try {
			ProjectsService projectsService = new ProjectsService();
			AddBidRequest addBidRequest = parseJSONRequest(request, AddBidRequest.class);
			if (addBidRequest.getBidAmount() == null) {
				Map<String, String> errors = new HashMap<>();
				errors.put("bidAmount", "bidAmount is required");
				FailResponse<Map<String, String>> failResponse = new FailResponse<>(errors);
				stringResponse = failResponse.toJSON();
			} else
				stringResponse = projectsService.handleAddBidRequest(addBidRequest, response, splittedURI[2], request);
		} catch (SQLException e) {
			ErrorResponse errorResponse = new ErrorResponse("Internal server error", 500);
			stringResponse = errorResponse.toJSON();
			response.setStatus(500);
		}
		this.sendResponse(stringResponse, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] splittedURI = request.getRequestURI().split("/");
		String stringResponse;

		try {
			ProjectsService projectsService = new ProjectsService();
			if (splittedURI.length == 2) {
				stringResponse = projectsService.handleAllProjectsRequest(response, request.getParameter("search"), request);
			} else if (splittedURI.length == 3) {
				stringResponse = projectsService.handleSingleProjectRequest(request, response, splittedURI[2]);
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
}