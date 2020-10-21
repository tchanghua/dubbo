package com.lagou.service.impl;

import com.lagou.service.HelloService;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;


@Service
public class HelloServiceImpl implements HelloService {


    @Override
    public String sayHello(String name, int timeToWait) {
        System.out.println("当前请求ip地址为：" + RpcContext.getContext().getAttachment("remoteHost"));
        return "hello:"+name + "；当前请求ip地址为：" + RpcContext.getContext().getAttachment("remoteHost");
    }
}
