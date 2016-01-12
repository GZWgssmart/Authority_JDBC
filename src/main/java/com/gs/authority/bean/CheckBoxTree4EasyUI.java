package com.gs.authority.bean;

import java.util.List;

/**
 * Created by WangGenshen on 1/12/16.
 */
public class CheckBoxTree4EasyUI {

    private String id;
    private String text;
    private String state;
    private boolean checked;
    private List<CheckBoxTree4EasyUI> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<CheckBoxTree4EasyUI> getChildren() {
        return children;
    }

    public void setChildren(List<CheckBoxTree4EasyUI> children) {
        this.children = children;
    }
}
