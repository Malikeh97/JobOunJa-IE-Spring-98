package gateways;

import domain.Project;
import domain.Skill;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class HttpGatewayTest {

	private IGateway httpGateway;

	@Before
	public void setUp() throws Exception {
		this.httpGateway = new HttpGateway();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getProjects() {
		List<Project> projects = this.httpGateway.getProjects();
		assertEquals("number of projects is wrong", 11, projects.size());
	}

	@Test
	public void getSkills() {
		List<Skill> skills = this.httpGateway.getSkills();
		assertEquals("number of skills is wrong", 17, skills.size());
	}
}