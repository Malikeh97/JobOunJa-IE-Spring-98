package services;

import Models.HTMLResponse;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class NotFoundPage implements IPage {
    @Override
    public void handleRequest(HttpExchange httpExchange) throws IOException {
        HTMLResponse htmlResponse = new HTMLResponse("PageNotFound", "<h1> 404 NOT FOUND</h1>");
        byte[] bytes = htmlResponse.getResponse(StandardCharsets.UTF_8);
        httpExchange.sendResponseHeaders(404, bytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
