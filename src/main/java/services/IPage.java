package services;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import Models.HTMLResponse;
import com.sun.net.httpserver.HttpExchange;

public abstract class IPage {
    public abstract void handleRequest(HttpExchange httpExchange) throws IOException;
    void sendResponse(HttpExchange httpExchange, int code, HTMLResponse htmlResponse) throws IOException {
        byte[] bytes = htmlResponse.getResponse(StandardCharsets.UTF_8);
        httpExchange.sendResponseHeaders(code, bytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
