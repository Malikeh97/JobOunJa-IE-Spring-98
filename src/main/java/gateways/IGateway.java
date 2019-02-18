package gateways;

import domain.Project;
import domain.Skill;

import java.util.List;

public interface IGateway {
	public List<Project> getProjects();
	public List<Skill> getSkills();
}
