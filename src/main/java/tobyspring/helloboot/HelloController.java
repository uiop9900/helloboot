package tobyspring.helloboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

public class HelloController { // fromController에서 받아서 사용한다.

    public String hello(String name) {
        return "hello" + name;
    }
}
