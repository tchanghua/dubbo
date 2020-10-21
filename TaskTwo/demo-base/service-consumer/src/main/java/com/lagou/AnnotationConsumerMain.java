package com.lagou;

import com.lagou.bean.ConsumerComponent;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AnnotationConsumerMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();
        ConsumerComponent service = context.getBean(ConsumerComponent.class);
        ExecutorService exc = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 2000; i++) {
            // service.sayHello2("world", 1000);
             Task task=new Task("sayHello",service) ;
             Task task2=new Task("sayHello2",service) ;
             Task task3=new Task("sayHello3",service) ;
             exc.execute(task);
             exc.execute(task2);
             exc.execute(task3);
        }
    }

    @Configuration
    @PropertySource("classpath:/dubbo-consumer.properties")
    @ComponentScan("com.lagou.bean")
    @EnableDubbo
    static class ConsumerConfiguration {

    }

    private static class Task implements Runnable {
        private String name;
        ConsumerComponent service;

        public Task(String name,ConsumerComponent service) {
            this.name = name;
            this.service=service ;
        }

        public void run() {
           if("sayHello".equals(name)){
               service.sayHello("hello world",2000) ;
           }else if("sayHello2".equals(name)){
               service.sayHello2("hello world",2000) ;
           }else{
               service.sayHello3("hello world",2000) ;
           }
        }
    }
}
