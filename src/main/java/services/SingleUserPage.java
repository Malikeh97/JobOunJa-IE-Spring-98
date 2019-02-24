package services;

import Models.HTMLResponse;
import Repository.InMemoryDBManager;
import com.sun.net.httpserver.HttpExchange;
import domain.User;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class SingleUserPage implements IPage {
    @Override
    public void handleRequest(HttpExchange httpExchange) throws IOException {
        httpExchange.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
        String[] tokens = httpExchange.getRequestURI().getPath().split("/");
        String id = tokens[tokens.length - 1];
        User user = InMemoryDBManager.shared.findUserById(id);
        if (user == null) {
            IPage notFoundPage = new NotFoundPage();
            notFoundPage.handleRequest(httpExchange);
            return;
        }
        String body = "<ul>" +
                "<li>id: " + user.getId() + "</li>" +
                "<li>first name: " + user.getFirstName() + "</li>" +
                "<li>last name: " + user.getLastName() + "</li>" +
                "<li>jobTitle: " + user.getJobTitle() + " </li>" +
                "<li>bio: " + user.getBio() + "</li>" +
                "</ul>";
        HTMLResponse htmlResponse = new HTMLResponse("User", body);

        byte[] bytes = htmlResponse.getResponse(StandardCharsets.UTF_8);
        httpExchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
