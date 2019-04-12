package controllers;

import api.ErrorResponse;
import services.ProjectsService;
import services.SkillsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/skills/*")
public class SkillsController extends BaseController {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] splittedURI = request.getRequestURI().split("/");
		SkillsService skillsService = new SkillsService();
		String stringResponse;
		if (splittedURI.length == 3) {
			stringResponse = skillsService.handleAllSkillsRequest(response);
		} else {
			ErrorResponse errorResponse = new ErrorResponse(request.getRequestURI() + " not found", 404);
			stringResponse = errorResponse.toJSON();
			response.setStatus(404);
		}
		this.sendResponse(stringResponse, response);
	}
}
