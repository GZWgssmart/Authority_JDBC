package com.gs.authority.service;

import com.gs.authority.bean.*;
import com.gs.authority.dao.AuthorityDAO;
import com.gs.authority.dao.RoleDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public Pager<Authority> queryByPagerAndRoleId(String roleId, int pageNo, int pageSize) {
        Pager pager = new Pager();
        pager.setPageNo(pageNo);
        pager.setPageSize(pageSize);
        try {
            pager = authorityDAO.queryByPagerAndRoleId(roleId, pager);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pager;
    }

    public List<CheckBoxTree4EasyUI> queryByRoleId(String roleId) {
        List<CheckBoxTree4EasyUI> allModules = new ArrayList<CheckBoxTree4EasyUI>();
        try {
            List<Authority> authorities = authorityDAO.queryAll();
            List<Authority> authorities1 = authorityDAO.queryByRoleId(roleId);
            if(authorities1 != null) {
                authorities.removeAll(authorities1);
            }
            if(authorities != null && authorities.size() > 0) {
                Module lastModule = authorities.get(0).getModule();
                CheckBoxTree4EasyUI lastModuleTree = new CheckBoxTree4EasyUI();
                lastModuleTree.setId(lastModule.getId() + "====M");
                lastModuleTree.setState("closed");
                lastModuleTree.setText(lastModule.getName());
                List<CheckBoxTree4EasyUI> lastModuleTreeChildren = new ArrayList<CheckBoxTree4EasyUI>();
                for (Authority authority : authorities) {
                    Module currentModule = authority.getModule();
                    if (currentModule.getName().equals(lastModule.getName())) {
                        CheckBoxTree4EasyUI auth = new CheckBoxTree4EasyUI();
                        auth.setId(authority.getId());
                        auth.setText(authority.getName());
                        lastModuleTreeChildren.add(auth);
                    } else {
                        lastModuleTree.setChildren(lastModuleTreeChildren);
                        allModules.add(lastModuleTree);
                        lastModule = currentModule;
                        lastModuleTree = new CheckBoxTree4EasyUI();
                        lastModuleTree.setId(lastModule.getId() + "====M");
                        lastModuleTree.setState("closed");
                        lastModuleTree.setText(lastModule.getName());
                        lastModuleTreeChildren = new ArrayList<CheckBoxTree4EasyUI>();
                        CheckBoxTree4EasyUI auth = new CheckBoxTree4EasyUI();
                        auth.setId(authority.getId());
                        auth.setText(authority.getName());
                        lastModuleTreeChildren.add(auth);
                    }
                }
                lastModuleTree.setChildren(lastModuleTreeChildren);
                allModules.add(lastModuleTree);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allModules;
    }

    public boolean addAuthoritiesForRole(String roleId, String allIds) {
        try {
            authorityDAO.addAuthoritiesForRole(roleId, allIds.split(","));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
