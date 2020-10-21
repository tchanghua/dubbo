package com.lagou;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;

import java.io.IOException;


@SpringBootApplication
@EnableAspectJAutoProxy
public class AnnotationConsumerMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(AnnotationConsumerMain.class, args);
    }

    @Configuration
    @PropertySource("classpath:/dubbo-consumer.properties")
    @ComponentScan("com.lagou.bean")
    @EnableDubbo
    static class ConsumerConfiguration {

    }
}
