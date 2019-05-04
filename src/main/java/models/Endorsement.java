package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.Column;
import utils.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endorsement {

	@Id
	private String id;

	@Column(name = "endorser_id")
	private String endorserId;

	@Column(name = "user_skill_id")
	private String userSkillId;

	@Column(name = "endorsed_id")
	private String endorsedId;
}
