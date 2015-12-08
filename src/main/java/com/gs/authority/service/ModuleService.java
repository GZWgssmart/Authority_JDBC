package com.gs.authority.service;

import com.gs.authority.bean.Module;
import com.gs.authority.bean.Pager;
import com.gs.authority.dao.ModuleDAO;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by WangGenshen on 12/3/15.
 */
public class ModuleService {

    private ModuleDAO moduleDAO;

    public ModuleService() {
        try {
            this.moduleDAO = new ModuleDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Module add(Module module) {
        try {
            return moduleDAO.add(module);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Module> queryAll() {
        try {
            return moduleDAO.queryAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Pager<Module> queryByPager(int pageNo, int pageSize) {
        Pager<Module> pager = new Pager<Module>();
        pager.setPageNo(pageNo);
        pager.setPageSize(pageSize);
        try {
            pager = moduleDAO.queryByPager(pager);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pager;
    }

}
