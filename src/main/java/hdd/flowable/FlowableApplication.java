package hdd.flowable;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
@ComponentScan(basePackages = { "hdd.flowable","org.flowable.ui.modeler","org.flowable.ui.common" })
@MapperScan(basePackages = "hdd.flowable.dao")
public class FlowableApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlowableApplication.class,args);
    }
}
