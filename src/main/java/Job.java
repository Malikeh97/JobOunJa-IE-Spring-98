import datalayer.datamappers.project.ProjectMapper;
import domain.Project;
import gateways.HttpGateway;
import gateways.IGateway;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job implements Runnable {
    private List<Project> projectList;
    private ProjectMapper projectMapper;
    private IGateway gateway;

    @Override
    public void run() {
        System.out.println("Hello run!" + new Date());
    }
}
