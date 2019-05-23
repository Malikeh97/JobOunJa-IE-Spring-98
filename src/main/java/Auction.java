import datalayer.datamappers.project.ProjectMapper;
import datalayer.datamappers.user.UserMapper;
import domain.Bid;
import domain.Project;
import domain.Skill;
import domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Auction implements Runnable  {
    private ProjectMapper projectMapper;
    private UserMapper userMapper;
    public void run() {
        try {
            List<Project> projects = projectMapper.findUncheckedProjects();
            for (Project project : projects) {
                String user_id = null;
                Double max_value = Double.MIN_VALUE;
                for (Bid bid : project.getBids()) {
                    User user = userMapper.findByIdWithSkills(bid.getUserId());
                    Double value = getBidEvaluation(project, user, bid);
                    if(max_value < value) {
                        user_id = user.getId();
                        max_value = value;
                    }
                }

                projectMapper.saveWinner(user_id, project.getId());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private double getBidEvaluation(Project project, User user, Bid bid) {
        HashMap<String, Integer> userSkills = new HashMap<>();
        List<Skill> projectSkills = project.getSkills();

        for(Skill skill: user.getSkills())
            userSkills.put(skill.getName(), skill.getPoint());

        double value = 0;
        for (Skill skill : projectSkills)
            value += Math.pow(userSkills.get(skill.getName()) - skill.getPoint(), 2.0);
        value *= 10000;
        value += (double) (project.getBudget() - bid.getBidAmount());

        return value;
    }

}
