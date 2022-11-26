package com.itheima.web.servlet;

import com.alibaba.fastjson.JSON;
import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import com.itheima.service.BrandService;
import com.itheima.service.impl.BrandServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/brand/*")
public class BrandServlet extends BaseServlet {
    private BrandService service=new BrandServiceImpl();
    public void selectAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Brand> brands = service.selectAll();
        String jsonString = JSON.toJSONString(brands);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }

    //添加企业信息
    public void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader br=req.getReader();
        String params = br.readLine();

        Brand brand = JSON.parseObject(params, Brand.class);

        service.add(brand);

        resp.getWriter().write("success");
    }
    //根据id查询出对象,用来回显数据
    public void selectById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader br=req.getReader();
        String params = br.readLine();

        int id=Integer.parseInt(params);

        Brand brand = service.selectById(id);

        String jsonString = JSON.toJSONString(brand);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);

    }

    //修改企业信息
    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader br=req.getReader();
        String params = br.readLine();


        Brand brand = JSON.parseObject(params, Brand.class);

        service.update(brand);

        resp.getWriter().write("success");
    }

    //删除企业信息
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader br=req.getReader();
        String params = br.readLine();

        int id=Integer.parseInt(params);
        int[] ids = new int[1];
        ids[0]=id;

        service.deleteByIds(ids);

        resp.getWriter().write("success");
    }

    public void deleteByIds(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        BufferedReader br=req.getReader();
        String params = br.readLine();

        int[] ids = JSON.parseObject(params, int[].class);
        service.deleteByIds(ids);

        resp.getWriter().write("success");
    }

    public void selectByPageAndCondition(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        String _currentPage = req.getParameter("currentPage");
        String _pageSize = req.getParameter("pageSize");

        int currentPage=Integer.parseInt(_currentPage);
        int pageSize=Integer.parseInt(_pageSize);

        //获取请求体数据
        BufferedReader br=req.getReader();
        String params = br.readLine();

        Brand brand = JSON.parseObject(params, Brand.class);

        PageBean<Brand> pageBean = service.selectByPageAndCondition(currentPage,pageSize,brand);

        String jsonString = JSON.toJSONString(pageBean);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);

    }

}
