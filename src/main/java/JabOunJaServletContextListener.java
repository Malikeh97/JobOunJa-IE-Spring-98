import datalayer.DBCPDBConnectionPool;
import datalayer.datamappers.bid.BidMapper;
import datalayer.datamappers.endorsment.EndorsementMapper;
import datalayer.datamappers.project.ProjectMapper;
import datalayer.datamappers.projectskill.ProjectSkillMapper;
import datalayer.datamappers.skill.SkillMapper;
import datalayer.datamappers.user.UserMapper;
import datalayer.datamappers.userskill.UserSkillMapper;
import domain.Project;
import domain.User;
import models.UserSkill;
import repository.InMemoryDBManager;
import domain.Skill;
import gateways.HttpGateway;
import gateways.IGateway;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class JabOunJaServletContextListener implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        try {
            IGateway gateway = new HttpGateway();
            UserMapper userMapper = new UserMapper();
            ProjectMapper projectMapper = new ProjectMapper();
            SkillMapper skillMapper = new SkillMapper();
            UserSkillMapper userSkillMapper = new UserSkillMapper();
            ProjectSkillMapper projectSkillMapper = new ProjectSkillMapper();
            BidMapper bidMapper = new BidMapper();
            EndorsementMapper endorsementMapper = new EndorsementMapper();
            Map<String, String> skills = new HashMap<>();
            List<models.Skill> skillsFromDb = skillMapper.findAll();

            for (models.Skill skill : skillsFromDb)
                skills.put(skill.getName(), skill.getId());

            if (skillMapper.countAll() == 0) {
                List<Skill> skillList = gateway.getSkills();
                for (Skill skill : skillList)
                    skillMapper.save(new models.Skill(UUID.randomUUID().toString(), skill.getName()));
            }

            scheduler = Executors.newSingleThreadScheduledExecutor();
            System.out.println(new Date());
            scheduler.scheduleAtFixedRate(
                    new Job(projectMapper, projectSkillMapper, skills, gateway),
                    0, 5, TimeUnit.MINUTES);

            scheduler.scheduleAtFixedRate(
                    new Auction(projectMapper, userMapper),
                    0, 1, TimeUnit.MINUTES);

        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }


    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
        try {
            DBCPDBConnectionPool.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                System.out.println(String.format("deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
                System.out.println(String.format("Error deregistering driver %s", driver));
                System.out.println(e.getLocalizedMessage());
            }
        }
    }
}
