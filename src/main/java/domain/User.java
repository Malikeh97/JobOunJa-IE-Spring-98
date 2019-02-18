package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private String id;
	private String firstName;
	private String lastName;
	private String jobTitle;
	private String profilePictureURL;
	private List<Skill> skills;
	private String bio;
}
