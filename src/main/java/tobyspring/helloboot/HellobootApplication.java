package tobyspring.helloboot;


import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
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
	 * servlet은 java지원 프로그램 -> 먼저 Servlet Container를 띄운다 -> 띄우려면 tomcat을 실행해야하는데 어떻게 띄우지? -> tom
	 */

	public static void main(String[] args) {
//		new Tomcat().start(); -> 많은 설정값들이 존재해서 임베디드 tomcat을 직접 띄우는 건 쉽지않다.
		ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory(); // factory: tomcat사용을 도와주는 도우미 역할
		WebServer webServer = serverFactory.getWebServer(servletContext -> { // funtional Interface -> lamda로 대체 가능
			HelloController helloController = new HelloController();

			servletContext.addServlet("frontController", new HttpServlet() {
				@Override
				protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					// 인증, 보안, 다국어, 공통기능
					// 기존 servlet Container에서 매핑하던걸 이제는 fronController에서 매핑해야한다.
					if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
						String name = req.getParameter("name");

						String ret = helloController.hello(name); // 바인딩: 직적으로 웹요청과 응답을 다루는것들을 넣지 않고 String(java Type)으로 변환해서 넣어준다.

						resp.setStatus(HttpStatus.OK.value()); // 상태코드
						resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE); // header
						resp.getWriter().println(ret); // body
					} else if (req.getRequestURI().equals("/users")) {

					} else {
						resp.setStatus(HttpStatus.NOT_FOUND.value());
					}
				}
			}).addMapping("/*"); // 모든 요청을 받아서 공통처리 부분을 실행한다(AOP?)

		});
		webServer.start();

	}

}
