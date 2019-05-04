package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.Column;
import utils.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bid {
	@Id
	private String id;

	@Column(name = "project_id")
	private String projectId;

	@Column(name = "user_id")
	private String userId;
}
