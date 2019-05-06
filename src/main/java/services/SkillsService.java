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

			List<Skill> skillList = userSkillMapper.findSkillNotOwnedById("c6a0536b-838a-4e94-9af7-fcdabfffb6e5");

			if (skillList == null || skillList.isEmpty()) {
				ErrorResponse errorResponse = new ErrorResponse("No skill found", 404);
				response.setStatus(404);
				return errorResponse.toJSON();
			}

			List<domain.Skill> skillNames = new ArrayList<>();
			for(Skill skill : skillList)
				skillNames.add(new domain.Skill(skill.getName(),0,null));
			AllSkillsResponse successResponse = new AllSkillsResponse(skillNames);
			return successResponse.toJSON();
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
		}
		return null;
	}
}
