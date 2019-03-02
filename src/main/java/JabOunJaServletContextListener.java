import Repository.InMemoryDBManager;
import domain.Skill;
import domain.User;
import gateways.HttpGateway;
import gateways.IGateway;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.List;

@WebListener
public class JabOunJaServletContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		IGateway gateway = new HttpGateway();
		InMemoryDBManager.shared.setProjects(gateway.getProjects());
		InMemoryDBManager.shared.setSkills(gateway.getSkills());

		User newUser = createHardCodedUser();
		InMemoryDBManager.shared.addUser(newUser);
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
