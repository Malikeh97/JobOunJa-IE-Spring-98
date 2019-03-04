package services;

import Repository.InMemoryDBManager;
import domain.Project;
import domain.Skill;
import domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsersService {
	public void handleAllUsersRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<User> allUsers = InMemoryDBManager.shared.findAllUsers();
		User loggedInUser = InMemoryDBManager.shared.findUserById("1");
		if (allUsers == null || allUsers.size() < 2) {
			request.getRequestDispatcher("/notFound.jsp").forward(request, response);
			return;
		}
		allUsers.remove(loggedInUser);
		request.setAttribute("users", allUsers);
		request.getRequestDispatcher("/allUsers.jsp").forward(request, response);

	}
	public void handleSingleUserRequest(HttpServletRequest request, HttpServletResponse response, String id) throws ServletException, IOException {
		User user = InMemoryDBManager.shared.findUserById(id);
		User loggedInUser = InMemoryDBManager.shared.findUserById("1");
		if (user == null) {
			request.getRequestDispatcher("/notFound.jsp").forward(request, response);
			return;
		}
		if (user == loggedInUser) {
			List<Skill> skills = InMemoryDBManager.shared.findAllSkills();
			request.setAttribute("user", user);
			request.setAttribute("skills", skills);
			request.getRequestDispatcher("/myProfile.jsp").forward(request, response);
		} else {
			request.setAttribute("user", user);
			request.setAttribute("loggedInUser", loggedInUser);
			request.getRequestDispatcher("/anotherUser.jsp").forward(request, response);
		}
	}

	public void handleEndorseRequest(HttpServletRequest request, HttpServletResponse response, String id) throws ServletException, IOException {
		User user = InMemoryDBManager.shared.findUserById(id);
		User loggedInUser = InMemoryDBManager.shared.findUserById("1");
		for (Skill skill: user.getSkills()) {
			if((skill.getName()).equals(request.getParameter("skill"))) {
				skill.getEndorsers().add(loggedInUser.getId());
				skill.setPoint(skill.getPoint()+1);
				break;
			}
		}
		request.setAttribute("user", user);
		request.setAttribute("loggedInUser", loggedInUser);
		request.getRequestDispatcher("/anotherUser.jsp").forward(request, response);
	}

	public void handleAddSkillRequest(HttpServletRequest request, HttpServletResponse response, String id) throws ServletException, IOException {
		User loggedInUser = InMemoryDBManager.shared.findUserById("1");
		List<Skill> skills = InMemoryDBManager.shared.findAllSkills();
		boolean isRepeated = false;
		if(id.equals(loggedInUser.getId())) {
			for (Skill skill: loggedInUser.getSkills()) {
				if((skill.getName()).equals(request.getParameter("skill_name"))) {
					isRepeated = true;
					break;
				}
			}
			if (!isRepeated) {
				Skill newSkill = new Skill(request.getParameter("skill_name"), 0, new ArrayList<>());
				loggedInUser.getSkills().add(newSkill);
			}
		}

		request.setAttribute("user", loggedInUser);
		request.setAttribute("skills", skills);
		request.getRequestDispatcher("/myProfile.jsp").forward(request, response);
	}
}
