package services;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

public interface IPage {
    public void handleRequest(HttpExchange httpExchange) throws IOException;
}
