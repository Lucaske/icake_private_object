package com.imooc.icake.global;

import com.imooc.icake.biz.CatalogBiz;
import com.imooc.icake.biz.impl.CatalogBizImpl;
import com.imooc.icake.entity.Catalog;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class CatalogListen implements ServletContextListener {
    CatalogBiz catalogBiz=new CatalogBizImpl();
    public void contextInitialized(ServletContextEvent sce) {
        Catalog catalog=catalogBiz.getRoot();
        sce.getServletContext().setAttribute("root",catalog);
    }
}
