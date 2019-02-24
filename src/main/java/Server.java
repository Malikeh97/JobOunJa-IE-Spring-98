import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.StringTokenizer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import services.AllProjectsPage;
import services.IPage;
import services.NotFoundPage;
import services.SingleUserPage;

public class Server {
    private final String projectContext = "project";
    private final String userContext = "user";

    public void start() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started successfully!");
    }

    class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            StringTokenizer tokenizer = new StringTokenizer(httpExchange.getRequestURI().getPath(), "/");
            String context = tokenizer.nextToken();
            IPage page = new NotFoundPage();
            try {
                if (context.equals(projectContext)) {
                    if (tokenizer.hasMoreTokens()) {
                        page = new SingleUserPage();
                    } else {
                        page = new AllProjectsPage();
                    }
                } else if (context.equals(userContext)) {
                    if (tokenizer.hasMoreTokens()) {
                        page = new SingleUserPage();
                    }
                }
            } catch (IllegalArgumentException |
                     SecurityException e) {
                     e.printStackTrace();

            }

            page.handleRequest(httpExchange);
        }
    }
}
