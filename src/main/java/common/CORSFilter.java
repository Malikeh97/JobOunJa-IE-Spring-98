package common;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter(filterName = "cors")
public class CORSFilter implements Filter {

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;

            // Authorize (allow) all domains to consume the content
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Headers", "*");
            response.addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST, DELETE");

            // For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
            if (request.getMethod().equals("OPTIONS")) {
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
                return;
            }

            // pass the request along the filter chain
            filterChain.doFilter(request, servletResponse);

    }
}
