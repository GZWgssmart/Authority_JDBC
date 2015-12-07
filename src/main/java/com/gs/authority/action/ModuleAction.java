package com.gs.authority.action;

import com.alibaba.fastjson.JSON;
import com.gs.authority.bean.Module;
import com.gs.authority.bean.Pager;
import com.gs.authority.bean.Pager4EasyUI;
import com.gs.authority.service.ModuleService;
import com.gs.authority.util.Constants;
import com.gs.authority.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by WangGenshen on 12/3/15.
 */
public class ModuleAction extends HttpServlet {

    private ModuleService moduleService;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        moduleService = new ModuleService();
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String uri = req.getRequestURI();
        String path = uri.substring(uri.lastIndexOf("/") + 1);
        PrintWriter out = resp.getWriter();
        if (path.equals(Constants.ModuleAction.LIST)) {
            toListPage(req, resp);
        } else if (path.equals(Constants.ModuleAction.LIST_PAGER)) {
            out.println(listByPager(req));
        } else if(path.equals(Constants.ModuleAction.ADD)) {
            add(req);
        }
    }

    private void toListPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/pages/auth/module.jsp").forward(req, resp);
    }

    private String listByPager(HttpServletRequest req) {
        int pageNo = Integer.valueOf(req.getParameter("page"));
        int pageSize = Integer.valueOf(req.getParameter("rows"));
        Pager<Module> pager = moduleService.queryByPager(pageNo, pageSize);
        Pager4EasyUI<Module> modulePager = new Pager4EasyUI<Module>();
        modulePager.setTotal(pager.getTotalRecords());
        modulePager.setRows(pager.getObjects());
        return JSON.toJSONString(modulePager);
    }

    private String add(HttpServletRequest req) {
        return JSONUtil.result();
    }
}
