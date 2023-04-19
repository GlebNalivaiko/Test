package by.sunshine.service;

import by.sunshine.converter.OrderConverter;
import by.sunshine.dto.UserDto;
import by.sunshine.entity.*;
import by.sunshine.repository.OrderCartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderCartService {

    private final OrderCartRepository orderCartRepository;
    private final ProductService productService;
    private final CartService cartService;
    private final OrderConverter orderConverter;
    private final OrderService orderService;


    @Transactional
    public OrderCart findById(Long id) {
        OrderCart orderCart = orderCartRepository.findById(id).orElseThrow();
        orderCart.getOrders().forEach(order -> order.setPartOfWay(orderService.getPartOfWay(order)));
        return orderCart;
    }


    @Transactional
    public void makeOrder(Map<Long, Integer> productsAndQuantity, UserDto userDto) {
        Set<Long> ids = productsAndQuantity.keySet();
        List<Product> products = productService.findAllById(ids);
        List<Order> orders = new ArrayList<>();
        ids.forEach(id -> {
            Product product = products.stream().filter(prod -> prod.getId().equals(id)).findAny().orElseThrow();
            if (product.getQuantityInStock() >= productsAndQuantity.get(id)) {
                product.setQuantityInStock(product.getQuantityInStock() - productsAndQuantity.get(id));
                orders.add(orderConverter.convert(product, productsAndQuantity.get(id)));
            } else throw new NoSuchElementException();
        });
        OrderCart orderCart = orderCartRepository.findById(userDto.getOrderCartId()).orElseThrow();
        orderCart.getOrders().addAll(orders);
        userDto.getProductIds().removeAll(ids);
        cartService.deleteProductFromCart(userDto, products);
    }

    @Transactional
    public OrderCart confirm(Long orderId, UserDto userDto) {
        OrderCart orderCart = orderCartRepository.findById(userDto.getOrderCartId()).orElseThrow();
        Order order = orderCart.getOrders().stream().filter(ord -> ord.getId().equals(orderId)).findAny().orElseThrow();
        if (orderService.getPartOfWay(order) == 4) {
            orderCart.getOrders().remove(order);
            orderService.delete(order);
        }
        orderCart.getOrders().forEach(ord -> ord.setPartOfWay(orderService.getPartOfWay(ord)));
        return orderCart;
    }

    @Transactional
    public OrderCart cancel(Long orderId, UserDto userDto) {
        OrderCart orderCart = orderCartRepository.findById(userDto.getOrderCartId()).orElseThrow();
        Order order = orderCart.getOrders().stream().filter(ord -> ord.getId().equals(orderId)).findAny().orElseThrow();
        if (orderService.getPartOfWay(order) == 1) {
            Product product = productService.findById(order.getProductId());
            product.setQuantityInStock(product.getQuantityInStock() + order.getQuantity());
            orderCart.getOrders().remove(order);
            userDto.getProductIds().add(order.getProductId());
            Cart cart = cartService.findById(userDto.getCartId());
            cart.getProducts().add(product);
            orderService.delete(order);
        }
        orderCart.getOrders().forEach(ord -> ord.setPartOfWay(orderService.getPartOfWay(ord)));
        return orderCart;
    }
}
