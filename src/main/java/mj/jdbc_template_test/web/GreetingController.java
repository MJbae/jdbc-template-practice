package mj.jdbc_template_test.web;

import mj.jdbc_template_test.service.GreetingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GreetingController {

    private final GreetingService service;

    public GreetingController(GreetingService service) {
        this.service = service;
    }

    @RequestMapping("/")
    public @ResponseBody String defaultTest() {
        return "Hello, World";
    }

    @RequestMapping("/greeting")
    public @ResponseBody String greeting() {
        return service.greet();
    }

}
