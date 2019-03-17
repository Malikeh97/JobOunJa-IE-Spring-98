package services;

import API.AllProjectsResponse;
import API.ErrorResponse;
import API.SingleProjectResponse;
import API.SuccessResponse;
import API.data.SingleProjectData;
import Repository.InMemoryDBManager;
import domain.Bid;
import domain.Project;
import domain.Skill;
import domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProjectsService {
	public String handleAllProjectsRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Project> projectList = InMemoryDBManager.shared.findAllProjects();
		User loggedInUser = InMemoryDBManager.shared.findUserById("1");
		if (projectList == null || projectList.isEmpty()) {
			ErrorResponse errorResponse = new ErrorResponse("No project found", 404);
			response.setStatus(404);
			return errorResponse.toJSON();
		}
		projectList.removeIf(project -> isForbidden(project, loggedInUser));

		AllProjectsResponse successResponse = new AllProjectsResponse(projectList);
		return successResponse.toJSON();
	}
	public String handleSingleProjectRequest(HttpServletRequest request, HttpServletResponse response, String id) throws ServletException, IOException {
		Project project = InMemoryDBManager.shared.findProjectById(id);

		boolean isBidAdded = false;
		User loggedInUser = InMemoryDBManager.shared.findUserById("1");
		if (project == null) {
			ErrorResponse errorResponse = new ErrorResponse("No project found", 404);
			response.setStatus(404);
			return errorResponse.toJSON();
		}
		if (isForbidden(project, loggedInUser)) {
			ErrorResponse errorResponse = new ErrorResponse("Access denied", 403);
			response.setStatus(403);
			return errorResponse.toJSON();
		}
		for(Bid bid : project.getBids()) {
			if(bid.getBiddingUser() == loggedInUser) {
				isBidAdded = true;
			}
		}
		SingleProjectResponse singleProjectResponse = new SingleProjectResponse(new SingleProjectData(project,isBidAdded));
		return singleProjectResponse.toJSON();

	}

	private boolean isForbidden(Project project, User loggedInUser) {
		for(Skill projectSkill : project.getSkills()) {
			Skill userSkill = loggedInUser.getSkills()
					.stream()
					.filter(skill -> skill.getName().equals(projectSkill.getName()))
					.findAny()
					.orElse(null);
			if (userSkill == null || userSkill.getPoint() < projectSkill.getPoint())
				return true;
		}
		return false;
	}

	public void handleAddBidRequest(HttpServletRequest request, HttpServletResponse response, String id) throws ServletException, IOException {
		User loggedInUser = InMemoryDBManager.shared.findUserById("1");
		Project project = InMemoryDBManager.shared.findProjectById(id);
		Integer bidAmount = Integer.valueOf(request.getParameter("bidAmount"));
		if (bidAmount > project.getBudget()) {
			request.setAttribute("msg", "bidAmount was too big! try another value");
			request.setAttribute("isBidAdded", false);
		} else {
			Bid bid = new Bid(loggedInUser, project, bidAmount);
			project.getBids().add(bid);
			request.setAttribute("isBidAdded", true);
		}

		request.setAttribute("project", project);
		request.getRequestDispatcher("/singleProject.jsp").forward(request, response);
	}
}
