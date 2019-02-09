package Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProjectRequest {
    private String title;
    private List<Skill> skills;
    private Integer budget;
}
