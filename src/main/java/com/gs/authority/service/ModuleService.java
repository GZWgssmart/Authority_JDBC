package com.gs.authority.service;

import com.gs.authority.bean.Module;
import com.gs.authority.dao.ModuleDAO;

/**
 * Created by WangGenshen on 12/3/15.
 */
public class ModuleService {

    private ModuleDAO moduleDAO;

    public ModuleService() {
        this.moduleDAO = new ModuleDAO();
    }

    public Module add(Module module) {
        return moduleDAO.add(module);
    }

}
