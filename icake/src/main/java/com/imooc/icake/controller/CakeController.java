package com.imooc.icake.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.icake.biz.CakeBiz;
import com.imooc.icake.biz.CatalogBiz;
import com.imooc.icake.biz.impl.CakeBizImpl;
import com.imooc.icake.biz.impl.CatalogBizImpl;
import com.imooc.icake.entity.Cake;
import com.imooc.icake.entity.Catalog;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class CakeController {
    private CakeBiz cakeBiz=new CakeBizImpl();
    private CatalogBiz catalogBiz=new CatalogBizImpl();
    // /admin/Cake/list.do
    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageNum=request.getParameter("pageNum");
        if (pageNum==null){
            pageNum="1";
        }
        PageHelper.startPage(Integer.parseInt(pageNum),20);
        List<Cake> list=cakeBiz.getAll();
        PageInfo pageInfo=PageInfo.of(list);
        request.setAttribute("pageInfo",pageInfo);
        request.getRequestDispatcher("/WEB-INF/pages/admin/cake_list.jsp").forward(request,response);
    }
    // /admin/Cake/toAdd.do
    public void toAdd(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/admin/cake_add.jsp").forward(request,response);
    }
    // /admin/Cake/add.do
    public void add(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, FileUploadException {
        Cake cake=this.buildCake(request);
        cakeBiz.add(cake);
        response.sendRedirect("list.do");
    }
    // /admin/Cake/toEdit.do
    public void toEdit(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        int id=Integer.parseInt(request.getParameter("id"));
        Cake cake=cakeBiz.get(id);
        request.setAttribute("cake",cake);
        request.getRequestDispatcher("/WEB-INF/pages/admin/cake_edit.jsp").forward(request,response);
    }
    // /admin/Cake/edit.do
    public void edit(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, FileUploadException {
        Cake cake=this.buildCake(request);
        cakeBiz.edit(cake);
        response.sendRedirect("list.do");
    }
    // /admin/Cake/remove.do
    public void remove(HttpServletRequest request,HttpServletResponse response) throws IOException, FileUploadException {
        int id=Integer.parseInt(request.getParameter("id"));
        cakeBiz.remove(id);
        response.sendRedirect("list.do");
    }
    // /admin/Cake/detail.do
    public void detail(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        int id=Integer.parseInt(request.getParameter("id"));
        Cake cake=cakeBiz.get(id);
        request.setAttribute("cake",cake);
        request.getRequestDispatcher("/WEB-INF/pages/admin/cake_detail.jsp").forward(request,response);
    }


    private Cake buildCake(HttpServletRequest request) throws FileUploadException, UnsupportedEncodingException {
        Cake cake=new Cake();
        //文件上传基本操作
        //1、创建一个磁盘文件项工厂对象
        DiskFileItemFactory diskFileItemFactory=new DiskFileItemFactory();
        //2、创建一个核心解析类
        ServletFileUpload servletFileUpload=new ServletFileUpload(diskFileItemFactory);
        //3、解析request请求
        List<FileItem> list=servletFileUpload.parseRequest(request);
        //4、遍历集合，获得每个FileItem，判断表单项还是文件上传项
        for (FileItem fileItem:list){
            if (fileItem.isFormField()){
                if (fileItem.getFieldName().equals("title")){
                    cake.setTitle(fileItem.getString("utf-8"));
                }else if (fileItem.getFieldName().equals("status")){
                    cake.setStatus(fileItem.getString("utf-8"));
                }else if (fileItem.getFieldName().equals("cid")){
                    cake.setCid(Integer.parseInt(fileItem.getString("utf-8")));
                }else if (fileItem.getFieldName().equals("taste")){
                    cake.setTaste(fileItem.getString("utf-8"));
                }else if (fileItem.getFieldName().equals("sweetness")){
                    cake.setSweetness(Integer.parseInt(fileItem.getString("utf-8")));
                }else if (fileItem.getFieldName().equals("price")){
                    cake.setPrice(Double.parseDouble(fileItem.getString("utf-8")));
                }else if (fileItem.getFieldName().equals("weight")){
                    cake.setWeight(Double.parseDouble(fileItem.getString("utf-8")));
                }else if (fileItem.getFieldName().equals("size")){
                    cake.setSize(Integer.parseInt(fileItem.getString("utf-8")));
                }else if (fileItem.getFieldName().equals("material")){
                    cake.setMetarial(fileItem.getString("utf-8"));
                }else if (fileItem.getFieldName().equals("id")){
                    cake.setId(Integer.parseInt(fileItem.getString("utf-8")));
                }else if (fileItem.getFieldName().equals("image_path")&&cake.getImage_path()==null){
                    cake.setImage_path(fileItem.getString("utf-8"));
                }
            }else{
                if (fileItem.getFieldName().equals("image")){
                    if (fileItem.getSize()<=0) continue;
                    String ropath=request.getServletContext().getRealPath("");
                    String rootPath=request.getServletContext().getRealPath("/");
                    System.out.println(ropath);
                    System.out.println(rootPath);
                    String path=fileItem.getName();
                    String type=".jpg";
                    if (path.indexOf(".")!=-1){
                        type=path.substring(path.indexOf("."));
                    }
                    path="/download/images/"+System.currentTimeMillis()+type;
                    try {
                        fileItem.write(new File(rootPath+path));
                        cake.setImage_path(path);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return cake;
    }

}
