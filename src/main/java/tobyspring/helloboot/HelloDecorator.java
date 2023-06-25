package tobyspring.helloboot;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class HelloDecorator implements HelloService{
    private final HelloService helloService;

    public HelloDecorator(HelloService helloService) { // 그리고 주입받는다.
        this.helloService = helloService;
    }

    @Override
    public String sayHello(String name) { // 해당 클래스의 메소드를 오버라이드 해야한다.
        return "*" + helloService.sayHello(name) + "*";
    }
}
