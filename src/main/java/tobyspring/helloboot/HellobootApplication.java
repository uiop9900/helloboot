package tobyspring.helloboot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

@MySpringBootAnnotation
public class HellobootApplication {
	// 최초 spring boot가 만들어주는 main method와 동일하다.
	public static void main(String[] args) {
		SpringApplication.run(HellobootApplication.class, args);
	}

}
