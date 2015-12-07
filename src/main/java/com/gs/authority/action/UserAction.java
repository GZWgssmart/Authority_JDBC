package com.gs.authority.action;

import com.alibaba.fastjson.JSON;
import com.gs.authority.bean.Pager;
import com.gs.authority.bean.Pager4EasyUI;
import com.gs.authority.bean.User;
import com.gs.authority.service.AuthorityService;
import com.gs.authority.service.UserService;
import com.gs.authority.util.Constants;
import com.gs.authority.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
        if(path.equals(Constants.UserAction.LOGIN)) {
            login(req);
        } else if(path.equals(Constants.UserAction.ADMIN)) {
            toAdminPage(req, resp);
        } else if(path.equals(Constants.UserAction.LOGOUT)) {
            logout(req, resp);
        } else if(path.equals(Constants.UserAction.LIST_PAGER)) {
            out.println(listByPager(req));
        } else if(path.equals(Constants.UserAction.LIST)) {
            toListPage(req, resp);
        }
    }

    private void login(HttpServletRequest req) {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String code = req.getParameter("code");
        HttpSession session = req.getSession();
        String codeInSession = "";
        Object codeObj = session.getAttribute("code");
        if(codeObj != null) {
            codeInSession = codeObj.toString();
        }
        if(!codeInSession.equals(code)) {
            out.println(JSONUtil.errResult("验证码不正确"));
        } else {
            User user = new User();
            user.setName(name);
            user.setPassword(password);
            user = userService.queryWithRoles(user);
            if(user != null) {
                session.setAttribute(Constants.SESSION_CURRENT_USER, user);
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
        if(currentUserObj != null) {
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

}
