package com.gs.authority.dao;

import com.gs.authority.bean.Module;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by WangGenshen on 12/3/15.
 */
public class ModuleDAO extends BaseDAO<Module> {

    private Connection conn;

    public ModuleDAO() {
        this.conn = openConnection();
    }

    @Override
    public Module add(Module module) {
        String sql = "insert into t_module(id, name) values(?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, module.getId());
            stmt.setString(2, module.getName());
            if(stmt.execute()) {
                return module;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Module module) {
        return false;
    }

    @Override
    public boolean inactive(Module module) {
        return false;
    }

    @Override
    public boolean active(Module module) {
        return false;
    }

    @Override
    public Module queryById(Object id) {
        return null;
    }

    @Override
    public List<Module> queryAll() {
        return null;
    }
}
