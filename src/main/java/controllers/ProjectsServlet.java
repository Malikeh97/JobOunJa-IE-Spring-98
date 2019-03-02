package controllers;

import services.ProjectsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/projects/*")
public class ProjectsServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] splittedURI = request.getRequestURI().split("/");
		if (splittedURI.length == 3) {
			response.setContentType("text/html");

		} else if (splittedURI.length == 4) {
			ProjectsService projectsService = new ProjectsService();
			projectsService.handleSingleProjectRequest(request, response, splittedURI[3]);
		} else {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("Not Found!");
		}

	}
}
