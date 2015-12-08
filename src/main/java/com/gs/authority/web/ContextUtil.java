package com.gs.authority.web;

import com.gs.authority.bean.User;
import com.gs.authority.util.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by WangGenshen on 12/7/15.
 */
public class ContextUtil {

    public static User getCurrentUser(HttpSession session) {
        Object currentUserObj = session.getAttribute(Constants.SESSION_CURRENT_USER);
        if(currentUserObj != null) {
            return (User) currentUserObj;
        }
        return null;
    }

    public static void setCurrentUser(HttpSession session, User user) {
        session.setAttribute(Constants.SESSION_CURRENT_USER, user);
    }
}
