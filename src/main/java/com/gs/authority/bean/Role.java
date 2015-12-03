package com.gs.authority.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WangGenshen on 12/2/15.
 */
public class Role implements Serializable {

    private static final long serialVersionUID = -7216715542070013396L;
    private String id;
    private String name;
    private boolean allAuthority;

    private List<User> users;

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public boolean isAllAuthority() {
        return allAuthority;
    }

    public void setAllAuthority(boolean allAuthority) {
        this.allAuthority = allAuthority;
    }
}
