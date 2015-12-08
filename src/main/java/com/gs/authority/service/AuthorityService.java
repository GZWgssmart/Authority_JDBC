package com.gs.authority.service;

import com.gs.authority.bean.Authority;
import com.gs.authority.bean.Module;
import com.gs.authority.bean.Pager;
import com.gs.authority.bean.Role;
import com.gs.authority.dao.AuthorityDAO;
import com.gs.authority.dao.RoleDAO;

import java.sql.SQLException;

/**
 * Created by WangGenshen on 12/2/15.
 */
public class AuthorityService {

    private AuthorityDAO authorityDAO;

    public AuthorityService() {
        try {
            authorityDAO = new AuthorityDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Authority add(Authority authority) {
        try {
            return authorityDAO.add(authority);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean hasAuthority(Role role, String action) {
        boolean hasAuthority = false;
        try {
            if (!authorityDAO.shouldCheckAuthority(action)) {
                System.out.println(action + ", 此方法未被配置到权限角色关联表中,不需要检测此方法的权限");
                hasAuthority = true;
            } else if (authorityDAO.isAllAuthority(role.getId())) {
                System.out.println(role.getName() + ", 此角色具有所有权限,不需要进一步检测");
                hasAuthority = true;
            } else if (authorityDAO.queryByRoleIdAndAction(role.getId(), action) != null) {
                System.out.println(role.getName() + ", 此角色具有权限:" + action);
                hasAuthority = true;
            } else {
                System.out.println(role.getName() + ", 此角色不具有权限: " + action);
            }
            authorityDAO.closeConnection();
            return hasAuthority;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Pager<Authority> queryByPager(int pageNo, int pageSize) {
        Pager<Authority> pager = new Pager<Authority>();
        pager.setPageNo(pageNo);
        pager.setPageSize(pageSize);
        try {
            pager = authorityDAO.queryByPager(pager);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pager;
    }

}
