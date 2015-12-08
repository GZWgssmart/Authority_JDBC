package com.gs.authority.service;

import com.gs.authority.bean.Pager;
import com.gs.authority.bean.User;
import com.gs.authority.dao.UserDAO;

import java.sql.SQLException;

/**
 * Created by WangGenshen on 12/2/15.
 */
public class UserService {

    private UserDAO userDAO;

    public UserService() {
        try {
            userDAO = new UserDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public User add(User user) {
        try {
            return userDAO.add(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addRolesForUser(User user) {
        try {
            return userDAO.addRolesForUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Pager<User> queryByPager(int pageNo, int pageSize) {
        Pager<User> pager = new Pager<User>();
        pager.setPageNo(pageNo);
        pager.setPageSize(pageSize);
        try {
            return userDAO.queryByPager(pager);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User queryWithRoles(User user) {
        try {
            return userDAO.queryWithRoles(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public User queryByIdWithRoles(String userId) {
        try {
            return userDAO.queryByIdWithRoles(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateDefaultRole(User user) {
        try {
            return userDAO.updateDefaultRole(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
