package org.itechart.controller;

import lombok.RequiredArgsConstructor;
import org.itechart.dto.CardDto;
import org.itechart.dto.OrderDto;
import org.itechart.service.OrderService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final AmqpTemplate amqpTemplate;

    @PostMapping
    public ResponseEntity<OrderDto> add(@RequestBody OrderDto orderDto) {
        CardDto card = orderDto.getCard();
        card.setOrderUuid(orderDto.getUuid());
        amqpTemplate.convertAndSend("queue1", card);
        OrderDto newOrderDto = orderService.add(orderDto);
        return new ResponseEntity<>(newOrderDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> readAll() {
        List<OrderDto> orders = orderService.getAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<OrderDto> read(@PathVariable(name = "uuid") UUID uuid) {
        OrderDto orderDto = orderService.get(uuid);
        return orderDto != null
                ? new ResponseEntity<>(orderDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> update(@PathVariable(name = "uuid") UUID uuid, @RequestBody OrderDto orderDto) {
        orderDto.setUuid(uuid);
        OrderDto updatedOrder = orderService.update(orderDto);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable(name = "uuid") UUID uuid) {
        orderService.delete(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
