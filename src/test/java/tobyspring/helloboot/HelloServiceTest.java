package tobyspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.json.BasicJsonTester;

public class HelloServiceTest {
    // 해당 클래스의 메소드만을 test한다.

    @Test
    void simpleHelloService() {
        SimpleHelloService helloService = new SimpleHelloService();

        String res = helloService.sayHello("spring");

        Assertions.assertThat(res).isEqualTo("hello spring");
    }
}
