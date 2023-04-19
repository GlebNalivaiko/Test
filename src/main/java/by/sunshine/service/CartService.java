package by.sunshine.service;

import by.sunshine.dto.UserDto;
import by.sunshine.entity.Cart;
import by.sunshine.entity.Product;
import by.sunshine.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {


    private final CartRepository cartRepository;

    private final ProductService productService;


    public Cart findById(Long id) {
        return cartRepository.findById(id).orElseThrow();
    }


    @Transactional
    public void saveProductToCart(UserDto userDto, Long id) {
        userDto.getProductIds().add(id);
        if (userDto.getId() != null) {
            Product product = productService.findById(id);
            Cart cart = findById(userDto.getCartId());
            cart.getProducts().add(product);
        }
    }


    @Transactional
    public void deleteProductFromCart(UserDto userDto, Long id) {
        userDto.getProductIds().removeIf(prodId -> prodId.equals(id));
        if (userDto.getId() != null) {
            Cart cart = findById(userDto.getCartId());
            cart.getProducts().removeIf(product -> product.getId().equals(id));
        }
    }

    @Transactional
    public void deleteProductFromCart(UserDto userDto, List<Product> products) {
        Cart cart = findById(userDto.getCartId());
        products.forEach(cart.getProducts()::remove);
    }
}
