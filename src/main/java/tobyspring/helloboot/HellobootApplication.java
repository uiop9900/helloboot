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
		//spring container 생성 -> Application Context -> 사용하기 쉽게 존재하는 GenericApplicationContext
		GenericApplicationContext applicationContext = new GenericApplicationContext();
		// spring에서는 class 정보를 넘겨서 bean 을 등록하는 방식을 많이 쓴다.
		// Servlet 에서는 servlet을 만들어서 add 하는 방식이었다.
		applicationContext.registerBean(HelloController.class); // metadata를 넣어줬고
		// 이제 spring container가 가지가 가진 metadata들로 초기화 해서 bean들을 올리는데
		applicationContext.refresh();


		ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
		WebServer webServer = serverFactory.getWebServer(servletContext -> {

			servletContext.addServlet("frontController", new HttpServlet() {
				@Override
				protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					// 인증, 보안, 다국어, 공통기능
					if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
						String name = req.getParameter("name");

						HelloController helloController = applicationContext.getBean(HelloController.class);
						String ret = helloController.hello(name);

						resp.setContentType(MediaType.TEXT_PLAIN_VALUE);
						resp.getWriter().println(ret); // body
					} else {
						resp.setStatus(HttpStatus.NOT_FOUND.value());
					}
				}
			}).addMapping("/*"); // 모든 요청을 받아서 공통처리 부분을 실행한다(AOP?)

		});
		webServer.start();

	}

}
