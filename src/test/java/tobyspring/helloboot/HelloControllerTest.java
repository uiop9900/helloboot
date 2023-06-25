package tobyspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class HelloControllerTest {
    // helloController만을 테스트 하고싶은데, Service에 의존관계가 있다. 고립화시켜서 테스트한다.
    @Test
    void helloController() {
        HelloController helloController = new HelloController(name -> name); // 여기에서 service를 생성해 반환값을 정의한다. -> service와 무관하게 controller를 고립시킬수있다. == Stub

        String res = helloController.hello("test");

        Assertions.assertThat(res).isEqualTo("test");
    }

    @Test
    void failHelloController() {
        HelloController helloController = new HelloController(name -> name);

        Assertions.assertThatThrownBy( () -> {
            helloController.hello(null); // 예외가 발생해야 성공, 예외가 나지 않으면 실패
        }).isInstanceOf(IllegalArgumentException.class);


        Assertions.assertThatThrownBy( () -> {
            helloController.hello("");
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
