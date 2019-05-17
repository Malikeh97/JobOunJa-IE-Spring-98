import datalayer.datamappers.project.ProjectMapper;
import datalayer.datamappers.user.UserMapper;
import domain.Bid;
import domain.Project;
import domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.SQLException;
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
                for (Bid bid : project.getBids()) {
                    User user = userMapper.findByIdWithSkills(bid.getUserId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
