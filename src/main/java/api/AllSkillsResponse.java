package api;

import domain.Skill;

import java.util.List;

public class AllSkillsResponse extends SuccessResponse<List<Skill>> {
	public AllSkillsResponse(List<Skill> data) {
		super(data);
	}
}
