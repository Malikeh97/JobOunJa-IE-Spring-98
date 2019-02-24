package services;

import Models.HTMLResponse;
import Repository.InMemoryDBManager;
import com.sun.net.httpserver.HttpExchange;
import domain.Project;
import domain.User;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SingleProjectPage implements IPage {
    @Override
    public void handleRequest(HttpExchange httpExchange) throws IOException {
        httpExchange.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
        String[] tokens = httpExchange.getRequestURI().getPath().split("/");
        String id = tokens[tokens.length - 1];

        Project project = InMemoryDBManager.shared.findProjectById(id);
        if (project == null) {
            IPage notFoundPage = new NotFoundPage();
            notFoundPage.handleRequest(httpExchange);
            return;
        }
        String body = "    <ul>\n" +
                        "        <li>id: " + project.getId() + "</li>\n" +
                        "        <li>title: " + project.getTitle() + "</li>\n" +
                        "        <li>description: " + project.getDescription() + "</li>\n" +
                        "        <li>imageUrl: <img src=\"" + project.getImageURL() + "\" style=\"width: 50px; height: 50px;\"></li>\n" +
                        "        <li>budget: " + project.getBudget() + "</li>\n" +
                        "    </ul>";
        HTMLResponse htmlResponse = new HTMLResponse("Project", body);
        byte[] bytes = htmlResponse.getResponse(StandardCharsets.UTF_8);
        httpExchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
