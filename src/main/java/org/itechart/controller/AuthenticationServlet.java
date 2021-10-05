package org.itechart.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.itechart.exception.CarServiceServletException;
import org.itechart.repository.ClientRepository;
import org.itechart.service.ClientService;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.UUID;

import static org.itechart.util.Log.logger;

@WebServlet(name = "AuthenticationServlet", urlPatterns = "/authenticate")
public class AuthenticationServlet extends HttpServlet {

    ClientService clientService = new ClientService(new ClientRepository());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter pw = resp.getWriter();
        resp.setCharacterEncoding("UTF-8");
        pw.print("Authentication Page");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws CarServiceServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            if(clientService.authenticate(login, password)){
                HttpSession session = req.getSession(true);
                String token = UUID.randomUUID().toString();
                Cookie cookie = new Cookie("token", token);
                session.setAttribute("token", token);
                session.setMaxInactiveInterval(60);
                resp.addCookie(cookie);
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | SQLException e) {
            logger.error(e.getMessage());
            throw new CarServiceServletException("Internal server error. Try again later");
        }
    }
}
