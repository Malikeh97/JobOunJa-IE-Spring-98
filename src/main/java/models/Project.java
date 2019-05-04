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
public class Project {

	@Id
	private String id;

	private String title;

	private String description;

	@Column(name = "image_url")
	private String imageURL;

	private Integer budget;

	private Long deadline;

	@Column(name = "creation_date")
	private Long creationDate;

	@Column(name = "winner_id")
	@ForeignKey(foreignTable = "users", foreignField = "id")
	private String winnerId;
}
