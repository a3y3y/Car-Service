package org.itechart.service;

import org.itechart.mapper.*;
import org.itechart.repository.CarRepository;
import org.itechart.repository.ClientRepository;
import org.itechart.repository.OrderRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ServiceTestConfig {

    @Bean
    public CarService carService(CarRepository carRepository, CarMapper carMapper){
        return new CarServiceImpl(carRepository, carMapper);
    }

    @Bean
    public CarMapper carMapper(){
        return new CarMapperImpl();
    }

    @Bean
    public CarRepository carRepository(){
        return Mockito.mock(CarRepository.class);
    }

    @Bean
    public ClientService clientService(ClientRepository clientRepository,
                                       ClientMapper clientMapper, PasswordEncoder passwordEncoder){
        return new ClientServiceImpl(clientRepository, clientMapper, passwordEncoder);
    }

    @Bean
    public ClientMapper clientMapper(){
        return new ClientMapperImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public ClientRepository clientRepository(){
        return Mockito.mock(ClientRepository.class);
    }

    @Bean
    public OrderService orderService(OrderRepository orderRepository, CarRepository carRepository,
                                     ClientRepository clientRepository, OrderMapper orderMapper) {
        return new OrderServiceImpl(orderRepository, carRepository, clientRepository, orderMapper);
    }

    @Bean
    public OrderMapper orderMapper(){
        return new OrderMapperImpl();
    }

    @Bean
    public OrderRepository orderRepository(){
        return Mockito.mock(OrderRepository.class);
    }

    @Bean
    public AuthProvider authProvider(ClientService clientService, PasswordEncoder passwordEncoder){
        return new AuthProvider(clientService, passwordEncoder);
    }

    @Bean
    public Authentication authentication(){
        return Mockito.mock(Authentication.class);
    }

}