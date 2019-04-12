package services;

import Repository.InMemoryDBManager;
import api.AllProjectsResponse;
import api.AllSkillsResponse;
import api.ErrorResponse;
import domain.Skill;
import domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SkillsService {
	public String handleAllSkillsRequest(HttpServletResponse response) throws ServletException, IOException {
		List<Skill> skillList = InMemoryDBManager.shared.findAllSkills();
		User loggedInUser = InMemoryDBManager.shared.findUserById("1");
		if (skillList == null || skillList.isEmpty()) {
			ErrorResponse errorResponse = new ErrorResponse("No skill found", 404);
			response.setStatus(404);
			return errorResponse.toJSON();
		}
		skillList.removeIf(skill -> loggedInUser.getSkills().stream()
				.filter(userSkill -> userSkill.getName().equals(skill.getName()))
				.findAny()
				.orElse(null) != null);

		AllSkillsResponse successResponse = new AllSkillsResponse(skillList);
		return successResponse.toJSON();
	}
}
