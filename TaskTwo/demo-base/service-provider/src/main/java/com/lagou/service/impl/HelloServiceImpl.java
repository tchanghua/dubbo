package com.lagou.service.impl;

import com.lagou.service.HelloService;
import org.apache.dubbo.config.annotation.Service;


@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name, int timeToWait) {
        try {
            Thread.sleep(getRandom());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello:"+name;
    }

    @Override
    public String sayHello2(String name, int timeToWait) {
        try {
            Thread.sleep(getRandom());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello2:"+name;
    }

    @Override
    public String sayHello3(String name, int timeToWait) {
        try {
            Thread.sleep(getRandom());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello3:"+name;
    }

    /**
     * 产生0-100的随机数
     * @return
     */
    private int getRandom(){
        return (int)(Math.random()*101);
    }
}
