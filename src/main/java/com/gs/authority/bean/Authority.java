package com.gs.authority.bean;

import java.io.Serializable;

/**
 * Created by WangGenshen on 12/2/15.
 */
public class Authority implements Serializable {

    private static final long serialVersionUID = -7551190542666151863L;
    private String id;
    private String name;
    private String action;
    private Module module;

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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}
