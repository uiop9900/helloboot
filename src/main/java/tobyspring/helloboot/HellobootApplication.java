package tobyspring.helloboot;


import org.springframework.boot.SpringApplication;
import tobyspring.config.MySpringBootApplication;

@MySpringBootApplication
public class HellobootApplication {
	// 최초 spring boot가 만들어주는 main method와 동일하다.
	public static void main(String[] args) {
		SpringApplication.run(HellobootApplication.class, args);
	}

}
