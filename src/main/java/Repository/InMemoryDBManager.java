package Repository;

import domain.Project;
import domain.Skill;
import domain.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class InMemoryDBManager implements IDBManager {

	public static InMemoryDBManager shared = new InMemoryDBManager();

	private List<User> users;
	private List<Project> projects;
	private List<Skill> skills;

	private InMemoryDBManager() {
		this.users = new ArrayList<>();
		this.projects = new ArrayList<>();
		this.skills = new ArrayList<>();
	}

	@Override
	public User findUserById(String id) {
		return this.users
				.stream()
				.filter(user -> user.getId().equals(id))
				.findAny()
				.orElse(null);
	}

	@Override
	public List<Project> findAllProjects() {
		return this.projects;
	}

	@Override
	public List<Skill> findAllSkills() {
		return this.skills;
	}

	@Override
	public Project findProjectById(String id) {
		return this.projects
				.stream()
				.filter(project -> project.getId().equals(id))
				.findAny()
				.orElse(null);
	}

	@Override
	public void addUser(User newUser) {
		users.add(newUser);
	}
}
