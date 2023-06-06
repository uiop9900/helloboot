package tobyspring.helloboot;


import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.http.HttpHeaders;
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
			servletContext.addServlet("hello", new HttpServlet() { // servlet을 등록
				@Override
				protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					// 동적 body 만들기
					String name = req.getParameter("name");

					resp.setStatus(HttpStatus.OK.value()); // 상태코드
					resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE); // header
					resp.getWriter().println("Hello " + name); // body
				}
			}).addMapping("/hello"); // 해당 url에 이 servlet을 매핑한다.

		});
		webServer.start();

	}

}
