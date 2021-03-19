package nobel.springsecurity.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/", "/index"})
public class MainController {

    @GetMapping
    public String getMainPage() {
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }
}
