package controllers;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class BaseController extends HttpServlet {
    public void sendResponse(String stringResponse, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.print(stringResponse);
        out.flush();
    }
}
