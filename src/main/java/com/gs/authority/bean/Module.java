package com.gs.authority.bean;

import java.io.Serializable;

/**
 * Created by WangGenshen on 12/3/15.
 */
public class Module implements Serializable {
    private static final long serialVersionUID = 5563371061629102715L;
    private String id;
    private String name;

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
}
