package com.gs.authority.action;

import com.alibaba.fastjson.JSON;
import com.gs.authority.bean.Pager;
import com.gs.authority.bean.Pager4EasyUI;
import com.gs.authority.bean.Role;
import com.gs.authority.bean.User;
import com.gs.authority.service.UserService;
import com.gs.authority.util.Constants;
import com.gs.authority.util.JSONUtil;
import com.gs.authority.web.ContextUtil;

import javax.naming.Context;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by WangGenshen on 12/3/15.
 */
public class UserAction extends HttpServlet {

    private UserService userService;
    private PrintWriter out;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService = new UserService();
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/json;charset=utf-8");
        out = resp.getWriter();
        String uri = req.getRequestURI();
        String path = uri.substring(uri.lastIndexOf("/") + 1);
        if (path.equals(Constants.UserAction.LOGIN)) {
            login(req);
        } else if (path.equals(Constants.UserAction.ADMIN)) {
            toAdminPage(req, resp);
        } else if (path.equals(Constants.UserAction.LOGOUT)) {
            logout(req, resp);
        } else if (path.equals(Constants.UserAction.LIST_PAGER)) {
            out.println(listByPager(req));
        } else if (path.equals(Constants.UserAction.LIST)) {
            toListPage(req, resp);
        } else if (path.equals(Constants.UserAction.ADD)) {
            out.println(add(req));
        } else if(path.equals(Constants.UserAction.CHANGE_ROLE)) {
            out.println(changeRole(req));
        }
    }

    private void login(HttpServletRequest req) {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String code = req.getParameter("code");
        HttpSession session = req.getSession();
        String codeInSession = "";
        Object codeObj = session.getAttribute("code");
        if (codeObj != null) {
            codeInSession = codeObj.toString();
        }
        if (!codeInSession.equals(code)) {
            out.println(JSONUtil.errResult("验证码不正确"));
        } else {
            User user = new User();
            user.setName(name);
            user.setPassword(password);
            user = userService.queryWithRoles(user);
            if (user != null) {
                ContextUtil.setCurrentUser(session, user);
                out.println(JSONUtil.result());
            } else {
                out.println(JSONUtil.errResult("用户名或密码错误"));
            }
        }
        out.flush();
        out.close();
    }

    private void toAdminPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object currentUserObj = session.getAttribute(Constants.SESSION_CURRENT_USER);
        if (currentUserObj != null) {
            req.getRequestDispatcher("/pages/auth/admin.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/pages/auth/login.jsp");
        }
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        session.removeAttribute(Constants.SESSION_CURRENT_USER);
        resp.sendRedirect("/pages/auth/login.jsp");
    }

    private void toListPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/pages/auth/user.jsp").forward(req, resp);
    }

    private String listByPager(HttpServletRequest req) {
        int pageNo = Integer.valueOf(req.getParameter("page"));
        int pageSize = Integer.valueOf(req.getParameter("rows"));
        Pager<User> pager = userService.queryByPager(pageNo, pageSize);
        Pager4EasyUI<User> userPager = new Pager4EasyUI<User>();
        userPager.setTotal(pager.getTotalRecords());
        userPager.setRows(pager.getObjects());
        return JSON.toJSONString(userPager);
    }

    private String add(HttpServletRequest req) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(req.getParameter("name"));
        user.setPassword(req.getParameter("password"));
        Role defaultRole = new Role();
        String drString = req.getParameter("defaultRole");
        defaultRole.setId(drString.substring(0, drString.indexOf(",")));
        user.setDefaultRole(defaultRole);
        List<Role> roles = rolesList(req);
        if(roles != null) {
            user.setRoleNames(rolesName(roles));
            user.setRoles(roles);
        }
        user = userService.add(user);
        if(roles != null) {
            userService.addRolesForUser(user);
        }
        return user == null ? JSONUtil.errResult("添加用户失败，请稍候再试") : JSONUtil.result();
    }

    private String rolesName(List<Role> roles) {
        String roleNames = "";
        for (Role role : roles) {
            if (!roleNames.equals("")) {
                roleNames += ",";
            }
            roleNames += role.getName();
        }
        return roleNames;
    }

    private List<Role> rolesList(HttpServletRequest req) {
        String[] rolesString = req.getParameterValues("roles");
        List<Role> roles = null;
        if (rolesString != null && !rolesString.equals("")) {
            roles = new ArrayList<Role>();
            for (String roleString : rolesString) {
                String id = roleString.substring(0, roleString.indexOf(","));
                String name = roleString.substring(roleString.indexOf(",") + 1);
                Role role = new Role();
                role.setId(id);
                role.setName(name);
                roles.add(role);
            }
        }
        return roles;
    }

    private String changeRole(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = ContextUtil.getCurrentUser(session);
        Role role = new Role();
        role.setId(req.getParameter("defaultRoleId"));
        role.setName(req.getParameter("defaultRoleName"));
        user.setDefaultRole(role);
        boolean updated = userService.updateDefaultRole(user);
        ContextUtil.setCurrentUser(session, user);
        return updated ? JSONUtil.errResult("更新用户当前角色失败，请稍候再试") : JSONUtil.result();
    }

}
