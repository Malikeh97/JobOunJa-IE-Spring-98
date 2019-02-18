package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
	private String id;
	private String title;
	private String description;
	private String imageURL;
	private List<Skill> skills;
	private List<Bid> bids;
	private Integer budget;
	private OffsetDateTime deadline;
	private User winner;
}
