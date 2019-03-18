package services;

import api.*;
import api.data.SingleProjectData;
import Repository.InMemoryDBManager;
import domain.Bid;
import domain.Project;
import domain.Skill;
import domain.User;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectsService {
	public String handleAllProjectsRequest(HttpServletResponse response) throws ServletException, IOException {
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
	public String handleSingleProjectRequest(HttpServletResponse response, String id) throws ServletException, IOException {
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

	public String handleAddBidRequest(AddBidRequest request, HttpServletResponse response, String id) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		User loggedInUser = InMemoryDBManager.shared.findUserById("1");
		Project project = InMemoryDBManager.shared.findProjectById(id);
		if (request.getBidAmount() > project.getBudget()) {
			Map<String, String> failures = new HashMap<>();
			failures.put("bidAmount", "bidAmount was too big! try another value");
			FailResponse failResponse = new FailResponse(mapper.writeValueAsString(failures));
			response.setStatus(400);
			return failResponse.toJSON();
		} else if (project.getBids().stream().filter(bid -> bid.getBiddingUser().getId().equals(loggedInUser.getId())).findFirst().orElse(null) != null) {
			ErrorResponse errorResponse = new ErrorResponse("Cannot bid twice on a single project", 1000);
			response.setStatus(403);
			return errorResponse.toJSON();
		}
		Bid bid = new Bid(loggedInUser, project, request.getBidAmount());
		project.getBids().add(bid);
		AddBidResponse addBidResponse = new AddBidResponse("Bid added successfully");
		return addBidResponse.toJSON();
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
}
