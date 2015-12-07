package com.gs.authority.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WangGenshen on 12/2/15.
 */
public class User implements Serializable {

    private static final long serialVersionUID = 5869292351292164870L;
    private String id;
    private String name;
    private String password;

    private List<Role> roles;

    private String roleNames;

    private Role defaultRole;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Role getDefaultRole() {
        return defaultRole;
    }

    public void setDefaultRole(Role defaultRole) {
        this.defaultRole = defaultRole;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }
}
