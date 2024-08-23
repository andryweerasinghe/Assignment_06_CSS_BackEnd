/*
 * Author  : Mr.electrix
 * Project : CSS_Assignment_06_BackEnd
 * Date    : 8/17/24

 */

package lk.ijse.aad.css_assignment_06_backend;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class CORSFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        var origin = req.getHeader("Origin");
        var configuredOrigin = getServletContext().getInitParameter("origin");

        if (origin == null || !origin.contains(configuredOrigin)) {
            origin = "*";  // Allow all origins if no specific origin is configured
        }
        if (origin.contains(configuredOrigin)) {
            res.setHeader("Access-Control-Allow-Origin", "*");
            res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            res.setHeader("Access-Control-Allow-Headers", "Content-Type");
            res.setHeader("Access-Control-Expose-Headers", "Content-Type");
        }

        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            res.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        chain.doFilter(req, res);
    }
}
