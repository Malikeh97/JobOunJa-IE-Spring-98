//import datalayer.datamappers.project.ProjectMapper;
//import datalayer.datamappers.skill.SkillMapper;
//import datalayer.datamappers.user.UserMapper;
//import domain.Project;
//import repository.InMemoryDBManager;
//import domain.Skill;
//import gateways.HttpGateway;
//import gateways.IGateway;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;
//import java.sql.SQLException;
//import java.util.*;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//@WebListener
//public class JabOunJaServletContextListener implements ServletContextListener {
//
//	private ScheduledExecutorService scheduler;
//
//	@Override
//	public void contextInitialized(ServletContextEvent arg0) {
//		try{
//			IGateway gateway = new HttpGateway();
//			UserMapper userMapper = new UserMapper();
//			SkillMapper skillMapper = new SkillMapper();
//
//			List<Skill> skillList = gateway.getSkills();
//
//			User newUser1 = createHardCodedUser1();
//			userMapper.save(newUser1);
//			User newUser2 = createHardCodedUser2();
//			User newUser3 = createHardCodedUser3();
//
//			scheduler = Executors.newSingleThreadScheduledExecutor();
//			System.out.println(new Date());
//			scheduler.scheduleAtFixedRate(new Job(), 0, 5, TimeUnit.MINUTES);
//
//		} catch (SQLException e) {
//			System.out.println(e.getLocalizedMessage());
//		}
//
//
//	}
//
//	private static User createHardCodedUser1() {
//		List<Skill> newUserSkills = new ArrayList<>();
//		newUserSkills.add(new Skill("HTML", 5, new ArrayList<>()));
//		newUserSkills.add(new Skill("Javascript", 4, new ArrayList<>()));
//		newUserSkills.add(new Skill("C++", 2, new ArrayList<>()));
//		newUserSkills.add(new Skill("Java", 3, new ArrayList<>()));
//
//		User newUser = new User();
//		newUser.setId("1");
//		newUser.setFirstName("علی");
//		newUser.setLastName("شریف‌ زاده");
//		newUser.setJobTitle("برنامه نویس وب");
//		newUser.setProfilePictureURL("https://media.wired.com/photos/598e35994ab8482c0d6946e0/master/w_1164,c_limit/phonepicutres-TA.jpg");
//		newUser.setSkills(newUserSkills);
//		newUser.setBio("روی سنگ قبرم بنویسید: خدا بیامرز می خواست خیلی کارا بکنه ولی پول نداشت");
//		return newUser;
//	}
//
//
//	private static User createHardCodedUser2() {
//		List<Skill> newUserSkills = new ArrayList<>();
//		newUserSkills.add(new Skill("HTML", 10, new ArrayList<>()));
//		newUserSkills.add(new Skill("Javascript", 10, new ArrayList<>()));
//		newUserSkills.add(new Skill("C++", 5, new ArrayList<>()));
//		newUserSkills.add(new Skill("Java", 8, new ArrayList<>(Collections.singletonList("1"))));
//
//		User newUser = new User();
//		newUser.setId("2");
//		newUser.setFirstName("علی");
//		newUser.setLastName("طبا");
//		newUser.setJobTitle("برنامه نویس");
//		newUser.setProfilePictureURL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXogamGH0DqrmeiWioGZf8uOf9d9_eht0ttGJLSKxQRopOB9aI");
//		newUser.setSkills(newUserSkills);
//		newUser.setBio("نظری ندارم");
//		return newUser;
//	}
//
//	private static User createHardCodedUser3() {
//		List<Skill> newUserSkills = new ArrayList<>();
//		newUserSkills.add(new Skill("Python", 10, new ArrayList<>()));
//		newUserSkills.add(new Skill("Javascript", 2, new ArrayList<>()));
//		newUserSkills.add(new Skill("C++", 8, new ArrayList<>()));
//		newUserSkills.add(new Skill("ML", 8, new ArrayList<>(Arrays.asList("1", "2"))));
//		newUserSkills.add(new Skill("Java", 5, new ArrayList<>(Collections.singletonList("1"))));
//
//		User newUser = new User();
//		newUser.setId("3");
//		newUser.setFirstName("ملیکه");
//		newUser.setLastName("احقاقی");
//		newUser.setJobTitle("دانش پژوه");
//		newUser.setProfilePictureURL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTnM3fgM_n92xa8Eak83eMRtU266NMhOBjHHlhfdCnPyhP1Qlth");
//		newUser.setSkills(newUserSkills);
//		newUser.setBio("تولدم مبارک");
//		return newUser;
//	}
//
//	@Override
//	public void contextDestroyed(ServletContextEvent event) {
//		scheduler.shutdownNow();
//	}
//}
