import Models.AuctionRequest;
import Models.BidRequest;
import Models.AddProjectRequest;
import Models.RegisterRequest;
import javafx.util.Pair;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isFinished = false;
    private static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        while (!isFinished) {
            Pair<String, String> commandParts = getCommandParts();
            String commandName = commandParts.getKey();
            String commandData = commandParts.getValue();


            switch (commandName) {
                case "register":
                    System.out.println(commandData);
                    RegisterRequest registerRequest = mapper.readValue(commandData, RegisterRequest.class);
                    break;
                case "addProject":
                    System.out.println(commandData);
                    AddProjectRequest addProjectRequest = mapper.readValue(commandData, AddProjectRequest.class);
                    break;
                case "bid":
                    System.out.println(commandData);
                    BidRequest bidRequest = mapper.readValue(commandData, BidRequest.class);
                    break;
                case "auctionRequest":
                    System.out.println(commandData);
                    AuctionRequest auctionRequest = mapper.readValue(commandData, AuctionRequest.class);
                    isFinished = true;
                    break;
            }
        }
    }

    private static Pair<String, String> getCommandParts() {
        String command = scanner.nextLine();
        int spaceIndex = command.indexOf(" ");
        return new Pair<>(command.substring(0, spaceIndex), command.substring(spaceIndex));
    }
}
