

import Models.*;
import javafx.util.Pair;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
	private static Scanner scanner;
	private static boolean isFinished = false;
	private static ObjectMapper mapper = new ObjectMapper();

	private static HashMap<String, HashMap<String, Integer>> users = new HashMap<>();
	private static List<AddProjectRequest> projects = new ArrayList<>();
	private static List<BidRequest> bids = new ArrayList<>();

	public static void main(String[] args) {
		setUp();
		isFinished = false;
		while (!isFinished) {
			Pair<String, String> commandParts = getCommandParts();
			String commandName = commandParts.getKey();
			String commandData = commandParts.getValue();

			try {
				switch (commandName) {
					case "register":
						register(commandData);
						break;
					case "addProject":
						addProject(commandData);
						break;
					case "bid":
						bid(commandData);
						break;
					case "auction":
						auction(commandData);
						break;
				}

			} catch (IOException ex) {
				System.out.println(ex.getLocalizedMessage());
			}
		}
	}

	private static void setUp() {
		users = new HashMap<>();
		projects = new ArrayList<>();
		bids = new ArrayList<>();
		scanner = new Scanner(System.in);
	}

	private static void register(String commandData) throws IOException {
		RegisterRequest registerRequest = mapper.readValue(commandData, RegisterRequest.class);
		if (users.containsKey(registerRequest.getUsername())) {
			System.err.println("ERROR: " + registerRequest.getUsername() + ", This username exists.");
		} else {
			HashMap<String, Integer> skills = new HashMap<>();
			for (Skill skill : registerRequest.getSkills()) {
				skills.put(skill.getName(), skill.getPoints());
			}
			users.put(registerRequest.getUsername(), skills);
		}
	}

	private static void addProject(String commandData) throws IOException {
		AddProjectRequest addProjectRequest = mapper.readValue(commandData, AddProjectRequest.class);
		if (getProject(addProjectRequest.getTitle()) == null) {
			projects.add(addProjectRequest);
		} else {
			System.err.println("ERROR: " + "This project title exists.");
		}
	}

	private static void bid(String commandData) throws IOException {
		BidRequest bidRequest = mapper.readValue(commandData, BidRequest.class);
		if (isEligibleBid(bidRequest)) {
			BidRequest bid = getBid(bidRequest.getProjectTitle(), bidRequest.getBiddingUser());
			if (bid == null)
				bids.add(bidRequest);
			else
				bid.setBidAmount(bidRequest.getBidAmount());
		} else {
			System.err.println("ERROR: " + "Bid is not eligible.");
		}
	}

	private static void auction(String commandData) throws IOException {
		AuctionRequest auctionRequest = mapper.readValue(commandData, AuctionRequest.class);
		AddProjectRequest project = getProject(auctionRequest.getProjectTitle());
		if (project == null) {
			System.out.println("No such project found.");
		} else {
			List<BidRequest> projectBids = getAllProjectBids(auctionRequest.getProjectTitle());
			List<String> winners = getWinners(project, projectBids);
			if (winners == null || winners.size() == 0) {
				System.out.println("No bid for this project exists.");
			} else if (winners.size() == 1) {
				System.out.println("Winner is: " + winners.get(0));
			} else {
				System.out.println("Winners are: " + winners.toString());
			}
		}

		isFinished = true;
	}

	private static boolean isEligibleBid(BidRequest bidRequest) {
		AddProjectRequest project = getProject(bidRequest.getProjectTitle());
		HashMap<String, Integer> userSkills = users.get(bidRequest.getBiddingUser());
		if (project == null || userSkills == null)
			return false;

		if (bidRequest.getBidAmount() > project.getBudget())
			return false;

		for (Skill skill : project.getSkills()) {
			if (!userSkills.containsKey(skill.getName()))
				return false;
			if (skill.getPoints() > userSkills.get(skill.getName()))
				return false;
		}
		return true;
	}

	private static AddProjectRequest getProject(String projectTitle) {
		return projects.stream()
				.filter(project -> project.getTitle().equals(projectTitle))
				.findFirst()
				.orElse(null);
	}

	private static List<BidRequest> getAllProjectBids(String projectTitle) {
		return bids.stream()
				.filter(bid -> bid.getProjectTitle().equals(projectTitle))
				.collect(Collectors.toList());
	}

	private static BidRequest getBid(String projectTitle, String biddingUser) {
		return bids.stream()
				.filter(bid -> bid.getProjectTitle().equals(projectTitle) && bid.getBiddingUser().equals(biddingUser))
				.findFirst()
				.orElse(null);
	}

	private static List<String> getWinners(AddProjectRequest project, List<BidRequest> projectBids) {
		if (projectBids == null || projectBids.size() == 0) return new ArrayList<>();

		List<String> winners = new ArrayList<>();
		double maxPoint = Double.MIN_VALUE;
		for (BidRequest bid : projectBids) {
			double point = getBidEvaluation(project, bid);
			if (point > maxPoint) {
				maxPoint = point;
				winners.clear();
				winners.add(bid.getBiddingUser());
			} else if (point == maxPoint) {
				winners.add(bid.getBiddingUser());
			}
		}

		return winners;
	}

	private static double getBidEvaluation(AddProjectRequest project, BidRequest bid) {
		HashMap<String, Integer> userSkills = users.get(bid.getBiddingUser());
		double value = 0;
		for (Skill skill : project.getSkills()) {
			double userSkill = userSkills.get(skill.getName());
			value += Math.pow(skill.getPoints() - userSkill, 2.0);
		}
		value *= 10000;
		value += (double) (project.getBudget() - bid.getBidAmount());

		return value;
	}

	private static Pair<String, String> getCommandParts() {
		String command = scanner.nextLine();
		int spaceIndex = command.indexOf(" ");
		return new Pair<>(command.substring(0, spaceIndex), command.substring(spaceIndex));
	}
}
