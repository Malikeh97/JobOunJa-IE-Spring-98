package services;

import datalayer.datamappers.userskill.UserSkillMapper;
import models.Skill;
import api.AllSkillsResponse;
import api.ErrorResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SkillsService {
	public String handleAllSkillsRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			UserSkillMapper userSkillMapper = new UserSkillMapper();

			models.User user = (models.User) request.getAttribute("user");
			List<Skill> skillList = userSkillMapper.findSkillNotOwnedById(user.getId());

			List<domain.Skill> skillNames = new ArrayList<>();
			for (Skill skill : skillList)
				skillNames.add(new domain.Skill(skill.getId(), skill.getName(), 0, null));
			AllSkillsResponse successResponse = new AllSkillsResponse(skillNames);
			return successResponse.toJSON();
		} catch (SQLException e) {
			ErrorResponse errorResponse = new ErrorResponse("Internal server error", 500);
			response.setStatus(500);
			return errorResponse.toJSON();
		}
	}
}
