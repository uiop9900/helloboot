package tobyspring.helloboot;

import java.util.Objects;

public class HelloController {
    // helloService interface를 선언해서 생성자 주입받는다.

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    public String hello(String name) {
        return helloService.sayHello(Objects.requireNonNull(name));
    }
}
