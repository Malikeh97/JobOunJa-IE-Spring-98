import datalayer.datamappers.bid.BidMapper;
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
            SkillMapper skillMapper = new SkillMapper();
            UserSkillMapper userSkillMapper = new UserSkillMapper();
            BidMapper bidMapper = new BidMapper();
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
                    new Job(new ProjectMapper(), new ProjectSkillMapper(), skills, gateway),
                    0, 5, TimeUnit.MINUTES);

        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }


    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }
}
