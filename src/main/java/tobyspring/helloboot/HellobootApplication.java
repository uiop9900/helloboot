package tobyspring.helloboot;


import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class HellobootApplication {

	/**
	 * springboot는 초기에 servlet container를 신경쓰지 않아도 알아서 서버가 뜬다.
	 * 어떻게 가능한거지? -> 해당 어노테이션을 없애고 직접 servlet container를 띄워보자!
	 * servlet은 java지원 프로그램 -> 먼저 Servlet Container를 띄운다 -> 띄우려면 tomcat을 실행해야하는데 어떻게 띄우지? -> tomcat로 java로 되어있음.
	 */

	public static void main(String[] args) {
		// spring container가 초기화되는 시점에 Servelt contianer를 만든다.

		// spring container 만들고 bean 등록 후 초기화한다.
		GenericWebApplicationContext applicationContext = new GenericWebApplicationContext() {
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
		applicationContext.registerBean(HelloController.class);
		applicationContext.registerBean(SimpleHelloService.class);
		applicationContext.refresh();
	}

}
