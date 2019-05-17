import datalayer.datamappers.project.ProjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Auction implements Runnable  {
    private ProjectMapper projectMapper;
    public void run() {
        HashMap<String, String> winners =  projectMapper.findWinners();
        for (Map.Entry mapElement : winners.entrySet()) {
            String project_id = (String)mapElement.getKey();
            String winner_id = (String)mapElement.getValue();
            if(project_id != null && winner_id != null)
                projectMapper.saveWinner(winner_id, project_id);
        }
    }
}
