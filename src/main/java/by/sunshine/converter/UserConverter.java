package by.sunshine.converter;

import by.sunshine.dto.RegistrationRequestUser;
import by.sunshine.dto.UserDto;
import by.sunshine.entity.*;
import by.sunshine.security.CustomUserDetails;
import by.sunshine.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserConverter {
    private final ProductService productService;

    public User convert(RegistrationRequestUser requestUser, UserDto userDto) {
        User user = new User();
        Cart cart = new Cart();
        cart.setProducts(new HashSet<>(productService.findAllById(userDto.getProductIds())));
        user.setCart(cart);
        user.setOrderCart(new OrderCart());
        user.setName(requestUser.getName());
        user.setEmail(requestUser.getEmail());
        return user;
    }

    public void convert(User user, UserDto userDto) {
        userDto.setProductIds(user.getCart().getProducts().stream().map(Product::getId).collect(Collectors.toSet()));//продукты имеют связь  ван  ту мэни  и дефолтный фетч у него это лэзи, и дело в том что у jpa транзакция действует на весь endpoint и даже в thymeleaf но если мы сделаем редирект то транзациии конец, и тут с помощью логов можно проверить что продукты достаются именно тут
        userDto.setCartId(user.getCart().getId());
        userDto.setOrderCartId(user.getOrderCart().getId());
        userDto.setId(user.getId());
        userDto.setOrderIds(user.getOrderCart().getOrders().stream().map(Order::getId).collect(Collectors.toSet()));
        userDto.setName(user.getName());
    }

    public CustomUserDetails convert(User user) {
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setEmail(user.getEmail());
        customUserDetails.setPassword(user.getPassword());
        customUserDetails.setAuthorities(Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName())));
        return customUserDetails;
    }
}
