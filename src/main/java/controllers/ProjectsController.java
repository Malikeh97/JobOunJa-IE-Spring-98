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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] splittedURI = request.getRequestURI().split("/");
        String stringResponse;
        System.out.println(request);
        try {
            ProjectsService projectsService = new ProjectsService();
            if (splittedURI.length == 5) {
                if (splittedURI[4].equals("add_bid")) {
                    ObjectMapper mapper = new ObjectMapper();

                    AddBidRequest addBidRequest = parseJSONRequest(request, AddBidRequest.class);
                    if (addBidRequest.getBidAmount() == null) {
                        Map<String, String> failures = new HashMap<>();
                        failures.put("bidAmount", "bidAmount is required");
                        FailResponse<String> failResponse = new FailResponse<>(mapper.writeValueAsString(failures));
                        stringResponse = failResponse.toJSON();
                    } else {
                        stringResponse = projectsService.handleAddBidRequest(addBidRequest, response, splittedURI[3]);
                    }
                } else {
                    ErrorResponse errorResponse = new ErrorResponse(request.getRequestURI() + " not found", 404);
                    stringResponse = errorResponse.toJSON();
                    response.setStatus(404);
                }
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] splittedURI = request.getRequestURI().split("/");
        String stringResponse;

        try {
            ProjectsService projectsService = new ProjectsService();
            if (splittedURI.length == 3) {
                stringResponse = projectsService.handleAllProjectsRequest(response, request.getParameter("search"));
            } else if (splittedURI.length == 4) {
                stringResponse = projectsService.handleSingleProjectRequest(response, splittedURI[3]);
            } else {
                ErrorResponse errorResponse = new ErrorResponse(request.getRequestURI() + " not found", 404);
                stringResponse = errorResponse.toJSON();
                response.setStatus(404);
            }
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", 500);
            stringResponse = errorResponse.toJSON();
            response.setStatus(500);
        }
        this.sendResponse(stringResponse, response);
    }
}