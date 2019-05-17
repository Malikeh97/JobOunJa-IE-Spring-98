package common;

import api.ErrorResponse;
import api.FailResponse;
import datalayer.datamappers.user.UserMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Key;
import java.sql.SQLException;


//@WebFilter(filterName = "auth")
public class JWTAuthenticationFilter implements Filter {

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getRequestURI();
        if (path.equals("/login") || path.equals("/sign-up")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {

            String token = request.getHeader("Authorization");
            if (token == null) {
                FailResponse<String> failResponse = new FailResponse<>("No token provided");
                response.setStatus(401);
                PrintWriter out = response.getWriter();
                out.print(failResponse.toJSON());
                out.flush();
            }

            Jws<Claims> jws;
            Key key = Keys.hmacShaKeyFor("joboonjajoboonjajoboonjajoboonja".getBytes());
            try {
                jws = Jwts.parser()
                        .setSigningKey(key)
                        .parseClaimsJws(token);

                String username = jws.getBody().getSubject();

                UserMapper userMapper = new UserMapper();
                models.User user = userMapper.findByUsername(username);

                servletRequest.setAttribute("user", user);

                filterChain.doFilter(request, servletResponse);

            } catch (JwtException ex) {
                FailResponse<String> failResponse = new FailResponse<>("Invalid token");
                response.setStatus(403);
                PrintWriter out = response.getWriter();
                out.print(failResponse.toJSON());
                out.flush();
            } catch (SQLException e) {
                ErrorResponse errorResponse = new ErrorResponse("Internal server error", 500);
                response.setStatus(500);
                PrintWriter out = response.getWriter();
                out.print(errorResponse.toJSON());
                out.flush();
            }
        }
    }
}

