package com.itheima.web.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
public class BaseServlet extends HttpServlet {
    protected void service(HttpServletRequest req,HttpServletResponse resp){
        String uri = req.getRequestURI();
        int index=uri.lastIndexOf('/');
        String methodName=uri.substring(index+1);

        //类成员方法中的this,代表调用这个方法的那个对象!!!
        Class<? extends BaseServlet> cls=this.getClass();
        try {
            Method method=cls.getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
