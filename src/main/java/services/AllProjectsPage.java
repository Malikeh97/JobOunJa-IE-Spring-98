package services;

import Models.HTMLResponse;
import Repository.InMemoryDBManager;
import com.sun.net.httpserver.HttpExchange;
import domain.Project;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AllProjectsPage implements IPage {
    @Override
    public void handleRequest(HttpExchange httpExchange) throws IOException {
        httpExchange.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");

        List<Project> projectList = InMemoryDBManager.shared.findAllProjects();
        if (projectList == null || projectList.isEmpty()) {
            IPage notFoundPage = new NotFoundPage();
            notFoundPage.handleRequest(httpExchange);
            return;
        }
        String style = "        table {\n" +
                "            text-align: center;\n" +
                "            margin: 0 auto;\n" +
                "        }\n" +
                "        td {\n" +
                "            min-width: 300px;\n" +
                "            margin: 5px 5px 5px 5px;\n" +
                "            text-align: center;\n" +
                "        }";
        StringBuilder body = new StringBuilder("    <table>\n" +
                "        <tr>\n" +
                "            <th>id</th>\n" +
                "            <th>title</th>\n" +
                "            <th>budget</th>\n" +
                "        </tr>\n");
        for (Project project : projectList) {
            body.append("        <tr>\n" + "            <td>")
                    .append(project.getId()).append("</td>\n")
                    .append("            <td>")
                    .append(project.getTitle())
                    .append("</td>\n")
                    .append("            <td>")
                    .append(project.getBudget())
                    .append("</td>\n")
                    .append("        </tr>\n");
        }

        body.append("    </table>");
        HTMLResponse htmlResponse = new HTMLResponse("Project", style, body.toString());
        byte[] bytes = htmlResponse.getResponse(StandardCharsets.UTF_8);
        httpExchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}