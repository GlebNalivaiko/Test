package by.sunshine.service;

import by.sunshine.entity.Order;
import by.sunshine.constant.Status;
import by.sunshine.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    public void delete(Order order) {
        orderRepository.delete(order);
    }

    public int getPartOfWay(Order order) {
        LocalDateTime orderDate = order.getOrderDate();
        LocalDateTime arrivalDate = order.getArrivalDate();
        int partOfWay;
        if (arrivalDate.isBefore(LocalDateTime.now())) partOfWay = Status.ARRIVED;
        else if (LocalDateTime.now().isBefore(orderDate.plusHours(5))) partOfWay = Status.PACKING;
        else if (LocalDateTime.now().isBefore(orderDate.plusDays(3).plusHours(5))) partOfWay = Status.SHIPPED;
        else partOfWay = Status.IN_RECIPIENT_COUNTRY;
        return partOfWay;
    }
}
