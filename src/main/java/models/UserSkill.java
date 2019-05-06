package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.Column;
import utils.ForeignKey;
import utils.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSkill {
	@Id
	private String id;

	@Column(name = "user_id")
	@ForeignKey(foreignTable = "users", foreignField = "id")
	private String userId;

	@Column(name = "skill_id")
	@ForeignKey(foreignTable = "skills", foreignField = "id")
	private String skillId;
}
