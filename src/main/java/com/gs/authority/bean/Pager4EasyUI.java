package com.gs.authority.bean;

import java.util.List;

/**
 * Created by WangGenshen on 12/4/15.
 */
public class Pager4EasyUI<T> {
    private int total;
    private List<T> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
