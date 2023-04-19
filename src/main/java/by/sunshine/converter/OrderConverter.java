package by.sunshine.converter;

import by.sunshine.entity.Order;
import by.sunshine.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static by.sunshine.constant.Category.*;

@Component
@Slf4j
public class OrderConverter {


    public Order convert(Product product, Integer quantity) {
        Order order = new Order();
        order.setTitle(product.getTitle());
        order.setPrice(product.getPrice() * (1 - product.getDiscount()));
        order.setProductId(product.getId());
        order.setOrderDate(LocalDateTime.now().plusDays(0));
        order.setQuantity(quantity);
        order.setArrivalDate(getArrivalDate(product, quantity));
        order.setImage(product.getImages().get(0));
        return order;
    }

    private LocalDateTime getArrivalDate(Product product, Integer quantity) {
        LocalDateTime orderDate = LocalDateTime.now();
        int days = quantity;
        switch (product.getCategory().getName()) {
            case ELECTRONIC -> days += 20;
            case COSMETIC -> days += 15;
            case SPORT -> days += 23;
            case BOARD_GAMES -> days += 18;
            case MEN_CLOTHING -> days += 29;
            case WOMEN_CLOTHING -> days += 21;
            case HOUSEHOLD_ITEMS -> days += 9;
            case TOURISM -> days += 13;
            case PROPER_NUTRITION -> days += 5;
        }
        return orderDate.plusDays(days);
    }
}
