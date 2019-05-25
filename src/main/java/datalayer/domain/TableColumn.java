package datalayer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableColumn {
	private String name;
	private String type;
	private Boolean isPrimaryKey = false;
	private Boolean nullable = true;
	private Boolean unique = false;
	private String foreignKeyReference;
	private int length;
	private Method getter;
	private Method setter;
}
