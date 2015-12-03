package com.gs.authority.service;

import com.gs.authority.bean.Role;
import com.gs.authority.bean.User;
import com.gs.authority.dao.RoleDAO;

/**
 * Created by WangGenshen on 12/2/15.
 */
public class RoleService {

    private RoleDAO roleDAO;

    public RoleService() {
        roleDAO = new RoleDAO();
    }

    public Role add(Role role) {
        return roleDAO.add(role);
    }

    public Role queryByIdWithUsers(String roleId) {
        return roleDAO.queryByIdWithUsers(roleId);
    }

}
