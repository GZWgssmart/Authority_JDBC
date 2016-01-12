package com.gs.authority.action;

import com.alibaba.fastjson.JSON;
import com.gs.authority.bean.*;
import com.gs.authority.service.RoleService;
import com.gs.authority.util.Constants;
import com.gs.authority.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by WangGenshen on 12/4/15.
 */
public class RoleAction extends HttpServlet {

    private RoleService roleService;
    private PrintWriter out;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        roleService = new RoleService();
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/json;charset=utf-8");
        out = resp.getWriter();
        String uri = req.getRequestURI();
        String path = uri.substring(uri.lastIndexOf("/") + 1);
        if (path.equals(Constants.RoleAction.LIST)) {
            toListPage(req, resp);
        } else if (path.equals(Constants.RoleAction.LIST_PAGER)) {
            out.println(listByPager(req));
        } else if (path.equals(Constants.RoleAction.ADD)) {
            out.println(add(req));
        } else if(path.equals(Constants.RoleAction.ALL)) {
            out.println(all());
        } else if(path.equals(Constants.RoleAction.ROLE_AUTHORITY)) {
            toRoleAuthorityPage(req, resp);
        }
    }

    private void toListPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/pages/auth/role.jsp").forward(req, resp);
    }

    private void toRoleAuthorityPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roleId", req.getParameter("roleId"));
        req.getRequestDispatcher("/pages/auth/role_authority.jsp").forward(req, resp);
    }

    private String listByPager(HttpServletRequest req) {
        int pageNo = Integer.valueOf(req.getParameter("page"));
        int pageSize = Integer.valueOf(req.getParameter("rows"));
        Pager<Role> pager = roleService.queryByPager(pageNo, pageSize);
        Pager4EasyUI<Role> rolePager = new Pager4EasyUI<Role>();
        rolePager.setTotal(pager.getTotalRecords());
        rolePager.setRows(pager.getObjects());
        return JSON.toJSONString(rolePager);
    }

    private String all() {
        List<Role> roles = roleService.queryAll();
        List<ComboBox4EasyUI> comboBoxes = new ArrayList<ComboBox4EasyUI>();
        if (roles != null) {
            for (Role role : roles) {
                ComboBox4EasyUI comboBox = new ComboBox4EasyUI();
                comboBox.setId(role.getId() + "," + role.getName());
                comboBox.setText(role.getName());
                comboBoxes.add(comboBox);
            }
        }
        return JSON.toJSONString(comboBoxes);
    }

    private String add(HttpServletRequest req) {
        Role role = new Role();
        role.setId(UUID.randomUUID().toString());
        role.setName(req.getParameter("name"));
        String allAuthority = req.getParameter("allAuthority");
        role.setAllAuthority(allAuthority.equals("yes") ? true : false);
        role = roleService.add(role);
        return role == null ? JSONUtil.errResult("添加角色失败，请稍候再试") : JSONUtil.result();
    }

}
