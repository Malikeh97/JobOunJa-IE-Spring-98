package gateways;

import domain.Project;
import domain.Skill;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpGateway implements IGateway {
	private String baseURL = "http://142.93.134.194:8000/joboonja";
	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public List<Project> getProjects() {
		try {
			String projectJSON = getResponse("/project");
			return new ArrayList<>(Arrays.asList(mapper.readValue(projectJSON, Project[].class)));
		} catch (IOException ex) {
			System.err.println(ex.getLocalizedMessage());
		}
		return new ArrayList<>();
	}

	@Override
	public List<Skill> getSkills() {
		try {
			String projectJSON = getResponse("/skill");
			return new ArrayList<>(Arrays.asList(mapper.readValue(projectJSON, Skill[].class)));
		} catch (IOException ex) {
			System.err.println(ex.getLocalizedMessage());
		}
		return new ArrayList<>();
	}

	private String getResponse(String path) throws IOException {
		StringBuilder result = new StringBuilder();
		URL url = new URL(this.baseURL + path);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		return result.toString();
	}
}
