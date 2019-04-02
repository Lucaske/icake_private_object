package com.imooc.icake.global;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GlobalController extends GenericServlet {
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        /*
        .do
        /login.do  DefaultController login
        /Cake/detail.do CakeController detail
        /admin/Cake/add.do CakeController add
         */
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        String pathName=request.getServletPath();
        if (pathName.indexOf("/admin")!=-1) {
            pathName=pathName.substring(7);
        }else{
            pathName=pathName.substring(1);
        }
        /*

        .do
        login.do  DefaultController login
        Cake/detail.do CakeController detail
        Cake/add.do CakeController add

        */
        int index=pathName.indexOf("/");
        String className=null;
        String methodName=null;
         if (index!=-1){
             className="com.imooc.icake.controller."+pathName.substring(0,index)+"Controller";
             methodName=pathName.substring(index+1,pathName.indexOf("."));
         }else{
             className="com.imooc.icake.controller.DefaultController";
             methodName=pathName.substring(0,pathName.indexOf("."));
         }
        try {
            Class clo=Class.forName(className);
            Object o=clo.newInstance();
            Method method=clo.getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            method.invoke(o,request,response);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
