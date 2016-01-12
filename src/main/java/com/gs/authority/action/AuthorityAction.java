package com.gs.authority.action;

import com.alibaba.fastjson.JSON;
import com.gs.authority.bean.Authority;
import com.gs.authority.bean.Module;
import com.gs.authority.bean.Pager;
import com.gs.authority.bean.Pager4EasyUI;
import com.gs.authority.service.AuthorityService;
import com.gs.authority.service.ModuleService;
import com.gs.authority.util.AuthorityUtil;
import com.gs.authority.util.Constants;
import com.gs.authority.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * Created by WangGenshen on 12/7/15.
 */
public class AuthorityAction extends HttpServlet {

    private AuthorityService authorityService;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        authorityService = new AuthorityService();
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/json;charset=utf-8");
        String uri = req.getRequestURI();
        String path = uri.substring(uri.lastIndexOf("/") + 1);
        PrintWriter out = resp.getWriter();
        if (path.equals(Constants.AuthorityAction.LIST)) {
            toListPage(req, resp);
        } else if (path.equals(Constants.AuthorityAction.LIST_PAGER)) {
            out.println(listByPager(req));
        } else if(path.equals(Constants.AuthorityAction.ADD)) {
            out.println(add(req));
        } else if (path.equals(Constants.AuthorityAction.LIST_PAGER_ROLE)) {
            out.println(listByPagerAndRole(req));
        } else if (path.equals(Constants.AuthorityAction.QUERY_BY_ROLE_ID)) {
            out.println(queryByRoleId(req));
        } else if (path.equals(Constants.AuthorityAction.ADD_AUTHORITY_FOR_ROLE)) {
            out.println(addAuthorityForRole(req));
        }
    }

    private void toListPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/pages/auth/authority.jsp").forward(req, resp);
    }

    private String listByPager(HttpServletRequest req) {
        int pageNo = Integer.valueOf(req.getParameter("page"));
        int pageSize = Integer.valueOf(req.getParameter("rows"));
        Pager<Authority> pager = authorityService.queryByPager(pageNo, pageSize);
        Pager4EasyUI<Authority> authorityPager = new Pager4EasyUI<Authority>();
        authorityPager.setTotal(pager.getTotalRecords());
        authorityPager.setRows(pager.getObjects());
        return JSON.toJSONString(authorityPager);
    }

    private String add(HttpServletRequest req) {
        Authority authority = new Authority();
        authority.setId(UUID.randomUUID().toString());
        authority.setAction(req.getParameter("action"));
        authority.setName(req.getParameter("name"));
        Module module = new Module();
        module.setId(req.getParameter("module"));
        authority.setModule(module);
        authority = authorityService.add(authority);
        return authority == null ? JSONUtil.errResult("添加权限失败，请稍候再试") : JSONUtil.result();
    }

    private String listByPagerAndRole(HttpServletRequest req) {
        String roleId = req.getParameter("roleId");
        int pageNo = Integer.valueOf(req.getParameter("page"));
        int pageSize = Integer.valueOf(req.getParameter("rows"));
        Pager<Authority> pager = authorityService.queryByPagerAndRoleId(roleId, pageNo, pageSize);
        Pager4EasyUI<Authority> authorityPager = new Pager4EasyUI<Authority>();
        authorityPager.setTotal(pager.getTotalRecords());
        authorityPager.setRows(pager.getObjects());
        return JSON.toJSONString(authorityPager);
    }

    private String queryByRoleId(HttpServletRequest req) {
        String roleId = req.getParameter("roleId");
        return JSON.toJSONString(authorityService.queryByRoleId(roleId));
    }

    public String addAuthorityForRole(HttpServletRequest req) {
        String roleId = req.getParameter("roleId");
        String ids = req.getParameter("ids");
        return authorityService.addAuthoritiesForRole(roleId, ids) ? JSONUtil.result() : JSONUtil.errResult("添加权限失败!");
    }

}
