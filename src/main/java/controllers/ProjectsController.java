package controllers;

import services.ProjectsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/projects/*")
public class ProjectsController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] splittedURI = request.getRequestURI().split("/");
        ProjectsService projectsService = new ProjectsService();
        if (splittedURI.length == 5) {
            if (splittedURI[4].equals("add_bid")) {
                projectsService.handleAddBidRequest(request, response, splittedURI[3]);
            } else {
                request.getRequestDispatcher("/notFound.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("/notFound.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] splittedURI = request.getRequestURI().split("/");
        ProjectsService projectsService = new ProjectsService();
        if (splittedURI.length == 3) {
            projectsService.handleAllProjectsRequest(request, response);
        } else if (splittedURI.length == 4) {
            projectsService.handleSingleProjectRequest(request, response, splittedURI[3]);
        } else {
            request.getRequestDispatcher("/notFound.jsp").forward(request, response);
        }

    }
}
