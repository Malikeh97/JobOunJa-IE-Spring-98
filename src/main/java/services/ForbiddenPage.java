package services;

import Models.HTMLResponse;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class ForbiddenPage extends IPage {
	@Override
	public void handleRequest(HttpExchange httpExchange) throws IOException {
		HTMLResponse htmlResponse = new HTMLResponse("ForbiddenPage", "<h1> 403 FORBIDDEN</h1>");
		this.sendResponse(httpExchange, 403, htmlResponse);
	}
}
