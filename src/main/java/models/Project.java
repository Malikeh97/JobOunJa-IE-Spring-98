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

	@Column
	private String title;

	@Column
	private String description;

	@Column(name = "image_url")
	private String imageURL;

	@Column(length = 20)
	private Integer budget;

	@Column(length = 20)
	private Long deadline;

	@Column(name = "creation_date", length = 20)
	private Long creationDate;

	@Column(name = "winner_id")
	@ForeignKey(foreignTable = "users", foreignField = "id")
	private String winnerId;

	@Column(name = "checked", nullable = false, length = 1)
	private Boolean isChecked = false;
}
