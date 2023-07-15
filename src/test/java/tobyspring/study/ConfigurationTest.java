package tobyspring.study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ConfigurationTest {

    @Test
    void configuration() {
//        MyConfig myConfig = new MyConfig();
//        Bean1 bean1 = myConfig.bean1();
//        Bean2 bean2 = myConfig.bean2();
//        Assertions.assertThat(bean1.common).isSameAs(bean2.common); 자바로 생성해서 비교하면 다른 객체이다.

        // 하지만 spring으로 생성하게 되면 프록시가 만들어져서 둘이 같은 객체이다.
        // 하나에 여러개가 의존하고 있으면, 의존대상들이 생길때마다 객체들이 생성되는 걸 막기 위해 스프링에서는 configuration이 붙은것들은 프록시 객체를 만들어서
        // 그런 상황들을 예방한다.
        // 하지만 configuration에서 proxyBeanMethods=false 로 설정해버리면 자바와 동일하게 결과가 나온다. (= 동일객체가 아니다.)
        // 다른 객체를 의존하지 않는다면, 굳이 프록시 객체를 만들지 않아도 되지 때문에 해당 설정을 false로 줄 수도 있다.
        AnnotationConfigApplicationContext ap = new AnnotationConfigApplicationContext();
        ap.register(MyConfig.class);
        ap.refresh();

        Bean1 bean1 = ap.getBean(Bean1.class);
        Bean2 bean2 = ap.getBean(Bean2.class);
        Assertions.assertThat(bean1.common).isSameAs(bean2.common);
    }

    @Configuration
    static class MyConfig {
        @Bean
        Common common() {
            return new Common();
        }

        @Bean
        Bean1 bean1() {
            return new Bean1(common());
        }

        @Bean
        Bean2 bean2() {
            return new Bean2(common());
        }
    }

    static class Bean1 {
        private final Common common;

        Bean1(Common common) {
            this.common = common;
        }
    }

    static class Bean2 {
        private final Common common;

        Bean2(Common common) {
            this.common = common;
        }
    }

    static class Common {
    }

}
