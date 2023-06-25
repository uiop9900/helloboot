package tobyspring.helloboot;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME) // runtime까지 annotation 정보가 유지되게끔
@Target(ElementType.TYPE) // type == class, interface, enum
@Configuration
@ComponentScan
public @interface MySpringBootAnnotation {
}
