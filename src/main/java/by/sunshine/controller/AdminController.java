package by.sunshine.controller;

import by.sunshine.converter.ProductConverter;
import by.sunshine.dto.ProductDto;
import by.sunshine.entity.Product;
import by.sunshine.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("admin")
public class AdminController {

    private final ProductService productService;
    private final ProductConverter productConverter;

    @GetMapping
    public String admin(Model model) {
        model.addAttribute("product", new Product());
        return "admin";
    }

    @PostMapping
    public @ResponseBody ProductDto product(@RequestBody ProductDto productDto) {
        productService.save(productConverter.convert(productDto));
        return productDto;
    }
}
