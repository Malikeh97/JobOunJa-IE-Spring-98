package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
	private String id;
	private String title;
	private String description;
	@JsonProperty("imageUrl")
	private String imageURL;
	private List<Skill> skills;
	private List<Bid> bids;
	private Integer budget;
	private Long deadline;
	private User winner;
}
