package by.sunshine.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("sunshine.by")
@Slf4j
public class MainPageController {
    @GetMapping
    public String mainPage() {
        return "main";
    }
}

