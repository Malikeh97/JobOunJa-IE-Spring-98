package services;

import api.*;
import api.data.SingleProjectData;
import datalayer.datamappers.bid.BidMapper;
import datalayer.datamappers.project.ProjectMapper;
import datalayer.datamappers.user.UserMapper;
import datalayer.datamappers.userskill.UserSkillMapper;
import repository.InMemoryDBManager;
import domain.Bid;
import domain.Project;
import domain.Skill;
import domain.User;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ProjectsService {
	private ProjectMapper projectMapper = new ProjectMapper();
	private UserMapper userMapper = new UserMapper();
	private BidMapper bidMapper = new BidMapper();

	public ProjectsService() throws SQLException {
	}

	public String handleAllProjectsRequest(HttpServletResponse response, String nameLike) throws ServletException, IOException, SQLException {
		List<Project> projectList = new ArrayList<>();
		if(nameLike == null)
			projectList = this.projectMapper.findAllForDomain();
		else
			projectList = this.projectMapper.findNameLike("%" + nameLike + "%");

		User loggedInUser = this.userMapper.findByIdWithSkills("c6a0536b-838a-4e94-9af7-fcdabfffb6e5");
		projectList.removeIf(project -> isForbidden(project, loggedInUser));

		AllProjectsResponse successResponse = new AllProjectsResponse(projectList);
		return successResponse.toJSON();
	}

	public String handleSingleProjectRequest(HttpServletResponse response, String id) throws ServletException, IOException, SQLException {
		Project project = this.projectMapper.findByIdForDomain(id);

		boolean isBidAdded = false;
		User loggedInUser = this.userMapper.findByIdWithSkills("c6a0536b-838a-4e94-9af7-fcdabfffb6e5");
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
			if(bid.getUserId().equals(loggedInUser.getId())) {
				isBidAdded = true;
			}
		}
		SingleProjectResponse singleProjectResponse = new SingleProjectResponse(new SingleProjectData(project,isBidAdded));
		return singleProjectResponse.toJSON();

	}

	public String handleAddBidRequest(AddBidRequest request, HttpServletResponse response, String id) throws ServletException, IOException, SQLException {
		ObjectMapper mapper = new ObjectMapper();

		User loggedInUser = this.userMapper.findByIdWithSkills("c6a0536b-838a-4e94-9af7-fcdabfffb6e5");
		Project project = this.projectMapper.findByIdWithBids(id);
		if (request.getBidAmount() > project.getBudget()) {
			Map<String, String> failures = new HashMap<>();
			failures.put("bidAmount", "bidAmount was too big! try another value");
			FailResponse failResponse = new FailResponse(mapper.writeValueAsString(failures));
			response.setStatus(400);
			return failResponse.toJSON();
		} else if (project.getBids().stream().filter(bid -> bid.getUserId().equals(loggedInUser.getId())).findFirst().orElse(null) != null) {
			ErrorResponse errorResponse = new ErrorResponse("Cannot bid twice on a single project", 1000);
			response.setStatus(403);
			return errorResponse.toJSON();
		}
		models.Bid bid = new models.Bid();
		bid.setId(UUID.randomUUID().toString());
		bid.setAmount(request.getBidAmount());
		bid.setProjectId(project.getId());
		bid.setUserId(loggedInUser.getId());
		if (this.bidMapper.save(bid) == null) {
			ErrorResponse errorResponse = new ErrorResponse("Internal server error", 500);
			response.setStatus(500);
			return errorResponse.toJSON();
		}
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
