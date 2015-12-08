package com.gs.authority.dao;

import com.gs.authority.bean.Module;
import com.gs.authority.bean.Pager;
import com.sun.corba.se.spi.orbutil.fsm.Guard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangGenshen on 12/3/15.
 */
public class ModuleDAO extends BaseDAO<Module> {

    private Connection conn;

    public ModuleDAO() throws SQLException, ClassNotFoundException {
        this.conn = openConnection();
    }

    @Override
    public Module add(Module module) throws SQLException {
        String sql = "insert into t_module(id, name) values(?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, module.getId());
        stmt.setString(2, module.getName());
        stmt.execute();
        int updateCount = stmt.getUpdateCount();
        stmt.close();
        closeConnection();
        return updateCount == 1 ? module : null;
    }

    @Override
    public boolean delete(Module module) throws SQLException {
        return false;
    }

    @Override
    public boolean inactive(Module module) throws SQLException {
        return false;
    }

    @Override
    public boolean active(Module module) throws SQLException {
        return false;
    }

    @Override
    public Module queryById(Object id) throws SQLException {
        return null;
    }

    @Override
    public List<Module> queryAll() throws SQLException {
        List<Module> modules = new ArrayList<Module>();
        String sql = "select * from t_module";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()) {
            Module module = new Module();
            module.setId(rs.getString("id"));
            module.setName(rs.getString("name"));
            modules.add(module);
        }
        return modules.size() > 0 ? modules : null;
    }

    @Override
    public Pager<Module> queryByPager(Pager pager) throws SQLException {
        String sql_count = "select count(id) as total from t_module";
        String sql = "select * from t_module limit " + pager.getBeginIndex() + ", " + pager.getPageSize();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            List<Module> modules = new ArrayList<Module>();
            rs.beforeFirst();
            while (rs.next()) {
                Module module = new Module();
                module.setId(rs.getString("id"));
                module.setName(rs.getString("name"));
                modules.add(module);
            }
            pager.setObjects(modules);
            pager.setTotalRecords(count(sql_count));
        }
        rs.close();
        stmt.close();
        closeConnection();
        return pager;
    }

}
