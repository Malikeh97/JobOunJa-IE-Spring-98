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
public class Endorsement {

	@Id
	private String id;

	@Column(name = "endorser_id")
	@ForeignKey(foreignTable = "users", foreignField = "id")
	private String endorserId;

	@Column(name = "skill_id")
	@ForeignKey(foreignTable = "skills", foreignField = "id")
	private String skillId;

	@Column(name = "endorsed_id")
	@ForeignKey(foreignTable = "users", foreignField = "id")
	private String endorsedId;
}
