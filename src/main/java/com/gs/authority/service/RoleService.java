package com.gs.authority.service;

import com.gs.authority.bean.Role;
import com.gs.authority.bean.User;
import com.gs.authority.dao.RoleDAO;

import java.sql.SQLException;

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

}
