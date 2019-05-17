package controllers;

import api.ErrorResponse;
import services.SkillsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/skills")
public class SkillsController extends BaseController {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SkillsService skillsService = new SkillsService();
		String stringResponse = skillsService.handleAllSkillsRequest(request, response);
		this.sendResponse(stringResponse, response);
	}
}
