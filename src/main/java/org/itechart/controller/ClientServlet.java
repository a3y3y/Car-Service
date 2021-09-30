package org.itechart.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.itechart.dto.CarDto;
import org.itechart.dto.ClientDto;
import org.itechart.repository.ClientRepository;
import org.itechart.service.ClientService;
import org.itechart.util.JsonConverter;

import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(name = "ClientServlet", urlPatterns = "/client")
public class ClientServlet extends HttpServlet {

    private ClientService clientService = new ClientService(new ClientRepository());
    private JsonConverter jsonConverter = new JsonConverter();

//    Как безопасно передавать пароль на сервер?

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonClient = req.getReader().lines().collect(Collectors.joining());
        ClientDto clientDto = jsonConverter.toObject(jsonClient, ClientDto.class);
        clientService.register(clientDto);
    }
}
