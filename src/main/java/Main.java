

import Repository.InMemoryDBManager;
import domain.Project;
import domain.Skill;
import domain.User;
import gateways.HttpGateway;

import java.util.*;


public class Main {

	public static void main(String[] args) {

		User newUser = createHardCodedUser();
		InMemoryDBManager.shared.addUser(newUser);

		HttpGateway httpGateway = new HttpGateway();
        List<Project> projectList = httpGateway.getProjects();
        List<Skill> skillList = httpGateway.getSkills();
		InMemoryDBManager.shared.setProjects(projectList);
		InMemoryDBManager.shared.setSkills(skillList);

		Server s = new Server();
		try {
			s.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static User createHardCodedUser() {
		List<Skill> newUserSkills = new ArrayList<>();
		newUserSkills.add(new Skill("HTML", 5));
		newUserSkills.add(new Skill("Javascript", 4));
		newUserSkills.add(new Skill("C++", 2));
		newUserSkills.add(new Skill("Java", 3));

		User newUser = new User();
		newUser.setId("1");
		newUser.setFirstName("علی");
		newUser.setLastName("شریف‌ زاده");
		newUser.setJobTitle("برنامه نویس وب");
		newUser.setProfilePictureURL(null);
		newUser.setSkills(newUserSkills);
		newUser.setBio("روی سنگ قبرم بنویسید: خدا بیامرز می خواست خیلی کارا بکنه ولی پول نداشت");
		return newUser;
	}
}