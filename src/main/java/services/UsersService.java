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
	}
	public void handleSingleUserRequest(HttpServletRequest request, HttpServletResponse response, String id) throws ServletException, IOException {
		User user = InMemoryDBManager.shared.findUserById(id);
		User loggedInUser = InMemoryDBManager.shared.findUserById("1");
		if (user == null) {
			request.getRequestDispatcher("/notFound.jsp").forward(request, response);
			return;
		}
		if (user == loggedInUser) {

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
