package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

	@Id
	private String id;

	private String name;
}
