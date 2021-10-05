package org.itechart.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebFilter(filterName = "CarFilter", urlPatterns = "/cars/*")
public class CarFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        if(!httpRequest.getMethod().equalsIgnoreCase("GET")){
            HttpSession session = httpRequest.getSession();
            Cookie[] cookies = httpRequest.getCookies();
            Optional token = Arrays.stream(cookies)
                    .filter(n -> n.getName().equals("token")).map(n -> n.getValue())
                    .findFirst();
            if(token.isPresent()) {
                String tokenValue = (String) token.get();
                if(tokenValue.equals(session.getAttribute("token"))) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/authenticate");
                }
            } else {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/authenticate");
            }
        } else filterChain.doFilter(servletRequest, servletResponse);

    }
}
