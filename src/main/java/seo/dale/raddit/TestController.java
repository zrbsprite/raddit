package seo.dale.raddit;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping(value = "/test")
    public String test() {
        return "TEST";
    }

}
