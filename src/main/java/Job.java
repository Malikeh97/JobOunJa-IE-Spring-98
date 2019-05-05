import datalayer.datamappers.project.ProjectMapper;
import domain.Project;
import gateways.HttpGateway;
import gateways.IGateway;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.Column;
import utils.ForeignKey;
import utils.Id;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job implements Runnable {
    private ProjectMapper projectMapper;
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
                            null
                    ));
                    System.out.println(project.getTitle() + "added successfully");
                }

            }


        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }


    }
}
