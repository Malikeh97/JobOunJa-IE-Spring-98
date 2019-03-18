package controllers;

import api.AddBidRequest;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class BaseController extends HttpServlet {
    public void sendResponse(String stringResponse, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.print(stringResponse);
        out.flush();
    }

    public <T> T parseJSONRequest(HttpServletRequest request, Class<T> returnType) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null)
            stringBuilder.append(line);
        return mapper.readValue(stringBuilder.toString(), returnType);
    }
}
