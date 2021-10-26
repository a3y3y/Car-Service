package org.itechart.service;

import org.itechart.dto.CarDto;
import org.itechart.dto.ClientDto;
import org.itechart.dto.OrderDto;
import org.itechart.entity.Car;
import org.itechart.entity.Client;
import org.itechart.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@ContextConfiguration(classes=ServiceTestConfig.class)
class OrderServiceImplTest {

    private final String ORDER_STATUS = "Test status";
    private UUID ORDER_UUID = UUID.fromString("35c90465-de7e-4a78-9a6c-ee0577f30d3d");
    private final UUID CAR_UUID = UUID.fromString("35c90465-de7e-4a78-9a6c-ee0577f30d4d");
    private final UUID CLIENT_UUID = UUID.fromString("35c90465-de7e-4a78-9a6c-ee0577f30d5d");
    private final String CLIENT_LOGIN = "test";
    private final String CLIENT_PASSWORD = "test";

    @Autowired
    OrderService orderService;
    @Autowired
    TestEntityManager entityManager;

    @Test
    public void add_should_save_an_order() {
        Order order = newOrder();
        entityManager.persist(order.getCar());
        entityManager.persist(order.getClient());
        OrderDto orderDto = newOrderDto();
        OrderDto orderDto1 = orderService.add(orderDto);


        OrderDto testOrder = orderService.get(orderDto1.getUuid());

        assertThat(testOrder).hasFieldOrPropertyWithValue("status", "Processing payment");
        assertThat(testOrder.getCar()).isEqualTo(orderDto.getCar());
        assertThat(testOrder.getClient()).isEqualTo(orderDto.getClient());
    }

    @Test
    public void add_should_not_save_an_order_with_null_car_or_client_values() {
        Order order = newOrder();
        entityManager.persist(order.getCar());
        entityManager.persist(order.getClient());
        OrderDto orderDto = new OrderDto();
        orderDto.setUuid(ORDER_UUID);

        assertThat(orderService.add(orderDto)).isNull();

        CarDto carDto = new CarDto();
        ClientDto clientDto = new ClientDto();
        orderDto.setCar(carDto);
        orderDto.setClient(clientDto);

        assertThat(orderService.add(orderDto)).isNull();

    }

    @Test
    public void add_should_not_save_an_order_with_null_car_or_client_does_not_exist_in_database() {
        OrderDto orderDto = newOrderDto();
        OrderDto testOrder = orderService.add(orderDto);

        assertThat(testOrder).isNull();
    }


    @Test
    void get_should_find_an_order_by_uuid() {
        Order order = newOrder();
        entityManager.persist(order);

        OrderDto testOrder = orderService.get(order.getUuid());

        assertThat(testOrder).hasFieldOrPropertyWithValue("status", "Test status");
        assertThat(testOrder.getCar().getUuid()).isEqualTo(order.getCar().getUuid());
        assertThat(testOrder.getClient().getUuid()).isEqualTo(order.getClient().getUuid());
    }

    @Test
    void get_all_should_find_all_orders() {
        Order order = newOrder();
        entityManager.persist(order);

        List<OrderDto> orders = orderService.getAll();

        assertThat(orders).hasSize(1);
    }

    @Test
    void update_should_update_an_order() {
        Order order = newOrder();
        Integer orderId = entityManager.persistAndGetId(order, Integer.class);

        OrderDto orderDto = newOrderDto();;
        orderDto.setStatus("Changed test status");

        orderService.update(orderDto);
        Order testOrder = entityManager.find(Order.class, orderId);

        assertThat(testOrder).hasFieldOrPropertyWithValue("status", "Changed test status");
    }

    @Test
    void update_should_not_update_an_order_null_values() {
        Order order = newOrder();
        Integer orderId = entityManager.persistAndGetId(order, Integer.class);

        OrderDto orderDto = new OrderDto();
        orderDto.setUuid(ORDER_UUID);

        orderService.update(orderDto);
        Order testOrder = entityManager.find(Order.class, orderId);

        assertThat(testOrder).hasFieldOrPropertyWithValue("status", ORDER_STATUS);
    }

    @Test
    void delete_should_delete_an_order_by_uuid() {
        Order order = new Order();
        entityManager.persist(order);

        orderService.delete(order.getUuid());

        assertThat(orderService.getAll()).isEmpty();
    }

    private OrderDto newOrderDto(){
        OrderDto orderDto = new OrderDto();
        orderDto.setUuid(ORDER_UUID);
        CarDto carDto = new CarDto();
        carDto.setUuid(CAR_UUID);
        orderDto.setCar(carDto);
        ClientDto clientDto = new ClientDto();
        clientDto.setUuid(CLIENT_UUID);
        clientDto.setLogin(CLIENT_LOGIN);
        clientDto.setPassword(CLIENT_PASSWORD);
        orderDto.setClient(clientDto);
        orderDto.setStatus(ORDER_STATUS);
        return orderDto;
    }

    private Order newOrder(){
        Order order = new Order();
        order.setUuid(ORDER_UUID);
        Car car = new Car();
        car.setUuid(CAR_UUID);
        order.setCar(car);
        Client client = new Client();
        client.setUuid(CLIENT_UUID);
        client.setLogin(CLIENT_LOGIN);
        client.setPassword(CLIENT_PASSWORD);
        order.setClient(client);
        order.setStatus(ORDER_STATUS);
        return order;
    }

}