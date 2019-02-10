import Models.*;
import javafx.util.Pair;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isFinished = false;
    private static ObjectMapper mapper = new ObjectMapper();

    private static HashMap<String, HashMap<String, Integer>> users = new HashMap<>();
    private static List<AddProjectRequest> projects = new ArrayList<>();
    private static List<BidRequest> bids = new ArrayList<>();

    public static void main(String[] args){
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

    private static void register(String commandData) throws IOException {
        System.out.println(commandData);
        RegisterRequest registerRequest = mapper.readValue(commandData, RegisterRequest.class);
        if (users.containsKey(registerRequest.getUsername())) {
            System.out.println("ERROR: " + registerRequest.getUsername() + ", This username exists.");
        } else {
            HashMap<String, Integer> skills = new HashMap<>();
            for(Skill skill : registerRequest.getSkills()) {
                skills.put(skill.getName(), skill.getPoints());
            }
            users.put(registerRequest.getUsername(), skills);
        }
    }

    private static void addProject(String commandData) throws IOException {
        System.out.println(commandData);
        AddProjectRequest addProjectRequest = mapper.readValue(commandData, AddProjectRequest.class);
        if(getProject(addProjectRequest.getTitle()) == null) {
            System.out.println("ERROR: " + "This project title exists.");
        } else {
            projects.add(addProjectRequest);
        }

    }

    private static void bid(String commandData) throws IOException {
        System.out.println(commandData);
        BidRequest bidRequest = mapper.readValue(commandData, BidRequest.class);
        if(isEligibleBid(bidRequest)) {
            bids.add(bidRequest);
        } else {
            System.out.println("ERROR: " + "Bid is not eligible.");
        }
    }

    private static boolean isEligibleBid(BidRequest bidRequest) {
        AddProjectRequest project = getProject(bidRequest.getProjectTitle());
        HashMap<String, Integer> userSkills = users.get(bidRequest.getBiddingUser());
        if(project == null || userSkills == null)
            return false;

        if(bidRequest.getBidAmount() > project.getBudget())
            return false;

        for (Skill skill : project.getSkills()) {
            if (!userSkills.containsKey(skill.getName()))
                return false;
            if(skill.getPoints() > userSkills.get(skill.getName()))
                return false;
        }
        return true;
    }

    private static AddProjectRequest getProject(String projectTitle) {
        return projects.stream()
                .filter(p -> p.getTitle().equals(projectTitle))
                .findFirst()
                .orElse(null);
    }

    private static void auction(String commandData) throws IOException {
        System.out.println(commandData);
        AuctionRequest auctionRequest = mapper.readValue(commandData, AuctionRequest.class);
        // Todo: implement functionality
        isFinished = true;
    }

    private static Pair<String, String> getCommandParts() {
        String command = scanner.nextLine();
        int spaceIndex = command.indexOf(" ");
        return new Pair<>(command.substring(0, spaceIndex), command.substring(spaceIndex));
    }

    // Todo: implement validation
    // Todo:test the code
}
