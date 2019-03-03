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
			List<String> endrosedSkills = new ArrayList<>();
			user.getSkills().forEach(skill -> {
				if (skill.getEndorsers() != null && skill.getEndorsers().indexOf(loggedInUser.getId()) >= 0)
					endrosedSkills.add(skill.getName());
			});
			request.setAttribute("user", user);
			request.setAttribute("endorsedSkills", endrosedSkills);
			request.getRequestDispatcher("/anotherUser.jsp").forward(request, response);
		}
	}

}
