package services;

import Models.HTMLResponse;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class NotFoundPage extends IPage {
    @Override
    public void handleRequest(HttpExchange httpExchange) throws IOException {
        HTMLResponse htmlResponse = new HTMLResponse("PageNotFound", "<h1> 404 NOT FOUND</h1>");
        this.sendResponse(httpExchange, 404, htmlResponse);

    }
}
