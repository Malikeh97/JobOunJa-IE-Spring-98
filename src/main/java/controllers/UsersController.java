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
public class UsersController extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] splittedURI = request.getRequestURI().split("/");
		UsersService usersService = new UsersService();
		if (splittedURI.length == 4) {
			usersService.handleEndorseRequest(request, response, splittedURI[3]);
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
