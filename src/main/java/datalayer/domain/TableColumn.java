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
	private Method getter;
	private Method setter;
}
