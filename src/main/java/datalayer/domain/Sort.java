package datalayer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sort {
    private String fieldName;
    private Direction direction = Direction.DESC;

    public enum Direction {
        ASC,
        DESC
    }
}
