package org.itechart.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.itechart.dto.ClientDto;
import org.itechart.repository.ClientRepository;
import org.itechart.service.ClientService;
import org.itechart.util.JsonConverter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

@WebServlet(name = "ClientServlet", urlPatterns = "/client")
public class ClientServlet extends HttpServlet {

    private ClientService clientService = new ClientService(new ClientRepository());
    private JsonConverter jsonConverter = new JsonConverter();

//    Как безопасно передавать пароль на сервер?

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String jsonClient = jsonConverter.toJson(clientService.read(login));
        sendJson(resp, jsonClient);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonClient = req.getReader().lines().collect(Collectors.joining());
        ClientDto clientDto = jsonConverter.toObject(jsonClient, ClientDto.class);
        clientService.register(clientDto);
    }

    private void sendJson(HttpServletResponse resp, String jsonString) throws IOException {
        PrintWriter pw = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        pw.print(jsonString);
    }
}
