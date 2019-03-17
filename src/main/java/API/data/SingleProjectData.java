package API.data;

import domain.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleProjectData {
    private Project project;
    private boolean isBidAdded;
}
