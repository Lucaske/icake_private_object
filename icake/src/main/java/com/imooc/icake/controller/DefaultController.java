package com.imooc.icake.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.icake.biz.AccountBiz;
import com.imooc.icake.biz.CakeBiz;
import com.imooc.icake.biz.CatalogBiz;
import com.imooc.icake.biz.impl.AccountBizImpl;
import com.imooc.icake.biz.impl.CakeBizImpl;
import com.imooc.icake.biz.impl.CatalogBizImpl;
import com.imooc.icake.entity.Account;
import com.imooc.icake.entity.Cake;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DefaultController {
    private AccountBiz accountBiz=new AccountBizImpl();
    private CatalogBiz catalogBiz=new CatalogBizImpl();
    private CakeBiz cakeBiz=new CakeBizImpl();
    // /toLogin.do
    public void toLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/admin/login.jsp").forward(request,response);
    }
    // /login.do
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name=request.getParameter("account");
        String password=request.getParameter("password");
        Account account=accountBiz.login(name,password);
        if (account==null){
            response.sendRedirect("toLogin.do");
        }else {
            request.getSession().setAttribute("ACCOUNT",name);
            response.sendRedirect("/admin/Cake/list.do");
        }

    }
    // /quit.do
    public void quit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("ACCOUNT",null);
        response.sendRedirect("toLogin.do");
    }

    // /index.do
    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cake cake=this.cakeBiz.getSpecial();
        request.setAttribute("cake",cake);
        List<Cake> cakeList=this.cakeBiz.getForIndex();
        request.setAttribute("list",cakeList);
        request.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(request,response);
    }
    // /list.do
    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int cid=Integer.parseInt(request.getParameter("cid"));
        //实现分页查询功能
        String pageNum=request.getParameter("pageNum");
        if (pageNum==null||pageNum.equals("")){
            pageNum="1";
        }
        PageHelper.startPage(Integer.parseInt(pageNum),12);
        List<Cake> cakeList=this.cakeBiz.getForCatalog(cid);
        PageInfo pageInfo=PageInfo.of(cakeList);
        request.setAttribute("pageInfo",pageInfo);
        request.setAttribute("cid",cid);
        request.getRequestDispatcher("/WEB-INF/pages/list.jsp").forward(request,response);
    }
    // /detail.do
    public void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id=Integer.parseInt(request.getParameter("id"));
        Cake cake=this.cakeBiz.get(id);
        request.setAttribute("cake",cake);
        request.getRequestDispatcher("/WEB-INF/pages/detail.jsp").forward(request,response);
    }
}
