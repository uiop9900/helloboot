package tobyspring.helloboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

@RequestMapping
public class HelloController {
    // helloService interface를 선언해서 생성자 주입받는다.

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    // 여기서 mapping을 하게 되면 DispatcherServlet이 class단위, method단위로 뒤져서 맞는 bean을 찾아낸다.
    // 이때 class 단위로 먼저 찾기때문에 class에도 annotation을 붙인다.
    @GetMapping("/hello")
    @ResponseBody
    public String hello(String name) {
        return helloService.sayHello(Objects.requireNonNull(name));
    }
}
