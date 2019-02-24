package services;

import Models.HTMLResponse;
import Repository.InMemoryDBManager;
import com.sun.net.httpserver.HttpExchange;
import domain.Project;
import domain.Skill;
import domain.User;

import java.io.IOException;

public class SingleProjectPage extends IPage {
    @Override
    public void handleRequest(HttpExchange httpExchange) throws IOException {
        httpExchange.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
        String[] tokens = httpExchange.getRequestURI().getPath().split("/");
        String id = tokens[tokens.length - 1];

        Project project = InMemoryDBManager.shared.findProjectById(id);
        User loggedInUser = InMemoryDBManager.shared.findUserById("1");
        if (project == null) {
            IPage notFoundPage = new NotFoundPage();
            notFoundPage.handleRequest(httpExchange);
            return;
        }
        if (isForbidden(project, loggedInUser)) {
            IPage forbiddenPage = new ForbiddenPage();
            forbiddenPage.handleRequest(httpExchange);
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
        this.sendResponse(httpExchange, 200, htmlResponse);
    }

    private boolean isForbidden(Project project, User loggedInUser) {
        for(Skill projectSkill : project.getSkills()) {
            Skill userSkill = loggedInUser.getSkills()
                    .stream()
                    .filter(skill -> skill.getName().equals(projectSkill.getName()))
                    .findAny()
                    .orElse(null);
            if (userSkill == null || userSkill.getPoint() < projectSkill.getPoint())
                return true;
        }
        return false;
    }
}
