package com.gs.authority.service;

import com.gs.authority.bean.Authority;
import com.gs.authority.bean.Role;
import com.gs.authority.dao.AuthorityDAO;
import com.gs.authority.dao.RoleDAO;

/**
 * Created by WangGenshen on 12/2/15.
 */
public class AuthorityService {

    private AuthorityDAO authorityDAO;

    public AuthorityService() {
        authorityDAO = new AuthorityDAO();
    }

    public Authority add(Authority authority) {
        return authorityDAO.add(authority);
    }

    public boolean hasAuthority(Role role, String action) {
        boolean hasAuthority = false;
        if (!authorityDAO.shouldCheckAuthority(action)) {
            System.out.println(action + ", 此方法未被配置到权限角色关联表中,不需要检测此方法的权限");
            hasAuthority = true;
        } else if (authorityDAO.isAllAuthority(role.getId())) {
            System.out.println(role.getName() + ", 此角色具有所有权限,不需要进一步检测");
            hasAuthority = true;
        } else if(authorityDAO.queryByRoleIdAndAction(role.getId(), action) != null) {
            hasAuthority = true;
        }
        authorityDAO.closeConnection();
        return hasAuthority;
    }

}
