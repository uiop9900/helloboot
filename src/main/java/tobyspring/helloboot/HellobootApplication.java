package tobyspring.helloboot;


import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;

@Configuration // spring에게 알린다. -> 해당 annotation은 가장 먼저 spring에게 등록당한다. 중요한 설정들이 많기떄문.
public class HellobootApplication {

	@Bean // spring에게 bean 생성되는 로직인걸 알린다.
	public HelloController helloController(HelloService helloService) { // spring이 알아서 주입하게끔 파라미터에 넣는다.
		return new HelloController(helloService);
	}

	@Bean
	public HelloService helloService() {
		return new SimpleHelloService();
	}
	public static void main(String[] args) {
		// spring container가 초기화되는 시점에 Servelt contianer를 만든다.

		// spring container 만들고 bean 등록 후 초기화한다.
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
			@Override
			protected void onRefresh() {
				super.onRefresh();

				// servlet container를 만들어서 frontController 역할의 servlet을 등록한다.
				ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
				WebServer webServer = serverFactory.getWebServer(servletContext -> {

					servletContext.addServlet("dispatcherServlet",
									new DispatcherServlet(this))
							.addMapping("/*");
				});
				webServer.start();
			}
		};

		// 코드단에서 bean을 생성했기때문에 register하는게 아니라 config가 있는 해당 클래스만을 알려준다.
		applicationContext.register(HellobootApplication.class);
		applicationContext.refresh();
	}

}
