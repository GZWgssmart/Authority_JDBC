package com.gs.authority.service;

import com.gs.authority.bean.Pager;
import com.gs.authority.bean.Role;
import com.gs.authority.bean.User;
import com.gs.authority.dao.RoleDAO;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by WangGenshen on 12/2/15.
 */
public class RoleService {

    private RoleDAO roleDAO;

    public RoleService() {
        try {
            roleDAO = new RoleDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Role> queryAll() {
        try {
            return roleDAO.queryAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Role add(Role role) {
        try {
            return roleDAO.add(role);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Role queryByIdWithUsers(String roleId) {
        try {
            return roleDAO.queryByIdWithUsers(roleId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Pager<Role> queryByPager(int pageNo, int pageSize) {
        Pager<Role> pager = new Pager<Role>();
        pager.setPageNo(pageNo);
        pager.setPageSize(pageSize);
        try {
            return roleDAO.queryByPager(pager);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
