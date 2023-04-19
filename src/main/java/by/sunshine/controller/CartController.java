package by.sunshine.controller;

import by.sunshine.dto.UserDto;
import by.sunshine.entity.Cart;
import by.sunshine.service.CartService;
import by.sunshine.service.ProductService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@Controller
@RequiredArgsConstructor
@RequestMapping("cart")
@Slf4j
public class CartController {


    private final CartService cartService;
    private final ProductService productService;

    @GetMapping()
    public String getCart(@SessionAttribute("user") UserDto userDto, Model model) {
        Cart cart;
        if (userDto.getId() != null) cart = cartService.findById(userDto.getCartId());
        else {
            cart = new Cart();
            cart.setProducts(new HashSet<>(productService.findAllById(userDto.getProductIds())));
        }
        model.addAttribute("cart", cart);
        return "cart";
    }

    @GetMapping("{product_id}")
    public String save(@PathVariable("product_id") Long productId,
                       @RequestHeader String referer,
                       @SessionAttribute("user") UserDto userDto) {
        cartService.saveProductToCart(userDto, productId);
        return "redirect:" + referer;
    }

    @GetMapping("delete/{product_id}")
    public String delete(@PathVariable("product_id") @NonNull Long productId,
                         @RequestHeader String referer,
                         @SessionAttribute("user") UserDto userDto) {
        cartService.deleteProductFromCart(userDto, productId);
        return "redirect:" + referer;
    }
}