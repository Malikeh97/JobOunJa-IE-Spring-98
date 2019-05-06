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
public class Bid {
	@Id
	private String id;

	@Column(name = "project_id")
	@ForeignKey(foreignTable = "projects", foreignField = "id")
	private String projectId;

	@Column(name = "user_id")
	@ForeignKey(foreignTable = "users", foreignField = "id")
	private String userId;

	private Integer amount;
}
