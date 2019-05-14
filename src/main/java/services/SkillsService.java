package services;

import datalayer.datamappers.skill.SkillMapper;
import datalayer.datamappers.user.UserMapper;
import datalayer.datamappers.userskill.UserSkillMapper;
import models.Skill;
import models.User;
import repository.InMemoryDBManager;
import api.AllSkillsResponse;
import api.ErrorResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SkillsService {
	public String handleAllSkillsRequest(HttpServletResponse response) throws ServletException, IOException {
		try {
			UserSkillMapper userSkillMapper = new UserSkillMapper();

			List<Skill> skillList = userSkillMapper.findSkillNotOwnedById("488a14ea-faac-41d6-a870-053fd80422c7");

			List<domain.Skill> skillNames = new ArrayList<>();
			for (Skill skill : skillList)
				skillNames.add(new domain.Skill(skill.getId(), skill.getName(), 0, null));
			AllSkillsResponse successResponse = new AllSkillsResponse(skillNames);
			return successResponse.toJSON();
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
		}
		return null;
	}
}
