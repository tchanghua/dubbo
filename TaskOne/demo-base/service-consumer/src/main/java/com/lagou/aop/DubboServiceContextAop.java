package com.lagou.aop;


import org.apache.catalina.connector.RequestFacade;
import org.apache.dubbo.rpc.RpcContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class DubboServiceContextAop {

    // com.lagou.service.HelloService
    @Pointcut("execution(* com.lagou.controller.*.*(..))")
    public void serviceApi() {
    }

    @Before("serviceApi()")
    public void dubboContext(JoinPoint jp) {
        Object[] args = jp.getArgs();
        RequestFacade requestFacade = (RequestFacade) args[0];
        String remoteHost = getIP(requestFacade);
        System.out.println("aop获取请求地址：" + remoteHost);
        RpcContext.getContext().setAttachment("remoteHost",remoteHost);
    }

    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (!checkIP(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (!checkIP(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!checkIP(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }
    private static boolean checkIP(String ip) {
        if (ip == null || ip.length() == 0 || "unkown".equalsIgnoreCase(ip)
                || ip.split(".").length != 4) {
            return false;
        }
        return true;
    }
}
