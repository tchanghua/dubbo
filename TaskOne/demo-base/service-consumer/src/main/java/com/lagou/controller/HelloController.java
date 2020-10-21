package com.lagou.controller;

import com.lagou.service.HelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
public class HelloController {

    @Reference
    private HelloService helloService;

    @RequestMapping("/hello")
    public String sayHello(HttpServletRequest request) {
        String name = "world";
        int timeToWait = 1000;
        return helloService.sayHello(name, timeToWait);
    }
}
