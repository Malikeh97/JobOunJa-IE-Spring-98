package services;

import Repository.InMemoryDBManager;
import domain.Project;
import domain.Skill;
import domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProjectsService {
	public void handleAllProjectsRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Project> projectList = InMemoryDBManager.shared.findAllProjects();
		User loggedInUser = InMemoryDBManager.shared.findUserById("1");
		if (projectList == null || projectList.isEmpty()) {
			request.getRequestDispatcher("/notFound.jsp").forward(request, response);
			return;
		}
		projectList.removeIf(project -> isForbidden(project, loggedInUser));

		request.setAttribute("projects", projectList);
		request.getRequestDispatcher("/allProjects.jsp").forward(request, response);
	}
	public void handleSingleProjectRequest(HttpServletRequest request, HttpServletResponse response, String id) throws ServletException, IOException {
		Project project = InMemoryDBManager.shared.findProjectById(id);
		User loggedInUser = InMemoryDBManager.shared.findUserById("1");
		if (project == null) {
			request.getRequestDispatcher("/notFound.jsp").forward(request, response);
			return;
		}
		if (isForbidden(project, loggedInUser)) {
			request.getRequestDispatcher("/forbidden.jsp").forward(request, response);
			return;
		}
		request.setAttribute("project", project);
		request.getRequestDispatcher("/singleProject.jsp").forward(request, response);
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
