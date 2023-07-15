package tobyspring.config.autoconfig;

import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import tobyspring.config.MyAutoConfiguration;

@MyAutoConfiguration
public class JettyWebServerConfig {

    // bean으로 올라갈때 메소드명으로 bean의 id가 생성되기에 동일 메소드면 에러가 난다.
    // 1. 아래처럼 명칭 따로 정하기 2.메소드 변경
    @Bean("jettyWebServerFactory")
    public ServletWebServerFactory servletWebServerFactory() {
        return new JettyServletWebServerFactory();
    }
}
