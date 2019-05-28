import datalayer.datamappers.project.ProjectMapper;
import datalayer.datamappers.projectskill.ProjectSkillMapper;
import domain.Project;
import domain.Skill;
import gateways.IGateway;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.SQLException;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job implements Runnable {
	private ProjectMapper projectMapper;
	private ProjectSkillMapper projectSkillMapper;
	private Map<String, String> skills;
	private IGateway gateway;

	@Override
	public void run() {
		try {
			List<Project> projectList = gateway.getProjects();
			Long maxCreationDate = projectMapper.maxCreationDate();
			for (Project project : projectList) {
				if (project.getCreationDate() > maxCreationDate) {
					projectMapper.save(new models.Project(project.getId(),
							project.getTitle(),
							project.getDescription(),
							project.getImageURL(),
							project.getBudget(),
							project.getDeadline(),
							project.getCreationDate(),
							null,
							false
					));
					for (Skill skill : project.getSkills()) {
						projectSkillMapper.save(new models.ProjectSkill(UUID.randomUUID().toString(),
								project.getId(),
								skills.get(skill.getName()),
								skill.getPoint()
						));
					}
					System.out.println(project.getTitle() + "added successfully");
				}

			}

			System.out.println("Winter is coming...");


		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
		}


	}
}
