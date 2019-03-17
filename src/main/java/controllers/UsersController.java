package controllers;

import services.ProjectsService;
import services.UsersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/*")
public class UsersController extends BaseController{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] splittedURI = request.getRequestURI().split("/");
		UsersService usersService = new UsersService();
        if (splittedURI.length == 5) {
			if (splittedURI[4].equals("add_skill")) {
                usersService.handleAddSkillRequest(request, response, splittedURI[3]);
            } else if (splittedURI[4].equals("endorse")) {
                usersService.handleEndorseRequest(request, response, splittedURI[3]);
            } else if (splittedURI[4].equals("delete_skill")) {
				usersService.handleDeleteRequest(request, response, splittedURI[3]);
			} else {
                request.getRequestDispatcher("/notFound.jsp").forward(request, response);
            }
		} else {
            request.getRequestDispatcher("/notFound.jsp").forward(request, response);
        }
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] splittedURI = request.getRequestURI().split("/");
		UsersService usersService = new UsersService();
		if (splittedURI.length == 3) {
			usersService.handleAllUsersRequest(request, response);
		} else if (splittedURI.length == 4) {
			usersService.handleSingleUserRequest(request, response, splittedURI[3]);
		} else {
			request.getRequestDispatcher("/notFound.jsp").forward(request, response);
		}

	}
}
