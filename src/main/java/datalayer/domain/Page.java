package datalayer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Page {
    private int startRow;
    private int pageSize;
    private Sort sort;
}
