package com.gs.authority.dao;

import com.gs.authority.bean.Authority;
import com.gs.authority.bean.Module;
import com.gs.authority.bean.Pager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangGenshen on 12/2/15.
 */
public class AuthorityDAO extends BaseDAO<Authority> {

    private Connection conn;

    public AuthorityDAO() throws SQLException, ClassNotFoundException {
        conn = openConnection();
    }

    @Override
    public Authority add(Authority authority) throws SQLException {
        String sql = "insert into t_authority(id, name, action, module_id) values(?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, authority.getId());
        stmt.setString(2, authority.getName());
        stmt.setString(3, authority.getAction());
        stmt.setString(4, authority.getModule().getId());
        stmt.execute();
        int updateCount = stmt.getUpdateCount();
        stmt.close();
        closeConnection();
        return updateCount == 1 ? authority : null;
    }

    @Override
    public boolean delete(Authority authority) throws SQLException {
        return false;
    }

    @Override
    public boolean inactive(Authority authority) throws SQLException {
        return false;
    }

    @Override
    public boolean active(Authority authority) throws SQLException {
        return false;
    }

    @Override
    public Authority queryById(Object id) throws SQLException {
        return null;
    }

    @Override
    public List<Authority> queryAll() throws SQLException {
        List<Authority> authorities = null;
        String sql = "select a.id, a.name, a.action, m.id as m_id, m.name as m_name from t_authority a " +
                "join t_module m on a.module_id = m.id group by m_name, m_id, a.id, a.name, a.action";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()) {
            authorities = new ArrayList<Authority>();
            rs.beforeFirst();
            while (rs.next()) {
                Authority authority = new Authority();
                authority.setId(rs.getString("id"));
                authority.setName(rs.getString("name"));
                authority.setAction(rs.getString("action"));
                Module module = new Module();
                module.setId(rs.getString("m_id"));
                module.setName(rs.getString("m_name"));
                authority.setModule(module);
                authorities.add(authority);
            }
        }
        rs.close();
        stmt.close();
        return authorities;
    }

    @Override
    public Pager<Authority> queryByPager(Pager pager) throws SQLException {
        String sql_count = "select count(id) as total from t_authority";
        String sql = "select a.id, a.name, a.action, m.id as m_id, m.name as m_name from t_authority a " +
                "join t_module m on a.module_id = m.id group by m.name, m.id, a.id, a.name, a.action limit " + pager.getBeginIndex() + ", " + pager.getPageSize();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            List<Authority> authorities = new ArrayList<Authority>();
            rs.beforeFirst();
            while (rs.next()) {
                Authority authority = new Authority();
                authority.setId(rs.getString("id"));
                authority.setName(rs.getString("name"));
                authority.setAction(rs.getString("action"));
                Module module = new Module();
                module.setId(rs.getString("m_id"));
                module.setName(rs.getString("m_name"));
                authority.setModule(module);
                authorities.add(authority);
            }
            pager.setObjects(authorities);
            pager.setTotalRecords(count(sql_count));
        }
        rs.close();
        stmt.close();
        closeConnection();
        return pager;
    }

    /**
     * 根据用户的角色id和所执行的方法名称判断该用户是否有执行方法的权限
     * <p>
     * 首先需要判断该方法是否配置到了权限与角色关联表中,如果未配置在此表中,说明不需要进一步检测权限,否则进一步判断此用户对应的角色
     * 是否拥有执行此方法的权限
     *
     * @param roleId
     * @param action
     * @return
     */
    public Authority queryByRoleIdAndAction(String roleId, String action) throws SQLException {
        Authority authority = null;
        String sql = "select a.id, a.name, a.action from t_authority a join t_authority_role ar "
                + "on a.id = ar.authority_id and ar.role_id = ? and a.action = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, roleId);
        stmt.setString(2, action);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            authority = new Authority();
            authority.setId(rs.getString("id"));
            authority.setName(rs.getString("name"));
            authority.setAction(rs.getString("action"));
        }
        rs.close();
        stmt.close();
        closeConnection();
        return authority;
    }

    /**
     * 如果某个方法被配置到了权限与角色关联表,说明只有部分角色可以使用该方法,则需要检测角色是否拥有该方法
     *
     * @param action
     * @return
     */
    public boolean shouldCheckAuthority(String action) throws SQLException {
        String sql = "select a.id from t_authority a join t_authority_role ar on a.id = ar.authority_id and a.action = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, action);
        ResultSet rs = stmt.executeQuery();
        boolean shouldCheck = rs.next() ? true : false;
        rs.close();
        stmt.close();
        return shouldCheck;
    }

    /**
     * 判断某个角色是否拥有所有权限,t_role表中all_authority为1表示具有所有权限
     *
     * @param roleId
     * @return
     */
    public boolean isAllAuthority(String roleId) throws SQLException {
        String sql = "select id from t_role where id = ? and all_authority = 1";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, roleId);
        ResultSet rs = stmt.executeQuery();
        boolean isAll = rs.next() ? true : false;
        rs.close();
        stmt.close();
        return isAll;
    }

    /**
     * 查询某个角色所有权限
     * @param roleId
     * @param pager
     * @return
     * @throws SQLException
     */
    public Pager<Authority> queryByPagerAndRoleId(String roleId, Pager pager) throws SQLException {
        String sql_count = "select count(a.id) from t_authority a join t_authority_role ar " +
                "on a.id = ar.authority_id and ar.role_id = '" + roleId + "'";
        String sql = "select a.id, a.name as a_name, a.action, m.id as m_id, m.name as m_name from t_authority a " +
                "join t_authority_role ar on a.id = ar.authority_id join t_module m " +
                "on a.module_id = m.id and ar.role_id = ? group by m.name, m.id, a.id, a.name, a.action limit " + pager.getBeginIndex() + ", " + pager.getPageSize();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, roleId);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()) {
            List<Authority> authorities = new ArrayList<Authority>();
            rs.beforeFirst();
            while (rs.next()) {
                Authority authority = new Authority();
                authority.setId(rs.getString("id"));
                authority.setName(rs.getString("a_name"));
                authority.setAction(rs.getString("action"));
                Module module = new Module();
                module.setId(rs.getString("m_id"));
                module.setName(rs.getString("m_name"));
                authority.setModule(module);
                authorities.add(authority);
            }
            pager.setTotalRecords(count(sql_count));
            pager.setObjects(authorities);
        }
        rs.close();
        stmt.close();
        closeConnection();
        return pager;
    }

    public List<Authority> queryByRoleId(String roleId) throws SQLException {
        List<Authority> authorities = null;
        String sql = "select a.id, a.name as a_name, a.action, m.id as m_id, m.name as m_name from t_authority a " +
                "join t_authority_role ar on a.id = ar.authority_id join t_module m " +
                "on a.module_id = m.id and ar.role_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, roleId);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()) {
            authorities = new ArrayList<Authority>();
            rs.beforeFirst();
            while (rs.next()) {
                Authority authority = new Authority();
                authority.setId(rs.getString("id"));
                authority.setName(rs.getString("a_name"));
                authority.setAction(rs.getString("action"));
                Module module = new Module();
                module.setId(rs.getString("m_id"));
                module.setName(rs.getString("m_name"));
                authority.setModule(module);
                authorities.add(authority);
            }
        }
        rs.close();
        stmt.close();
        closeConnection();
        return authorities;
    }

    public void addAuthoritiesForRole(String roleId, String[] authorityIds) throws SQLException {
        String sql = "insert into t_authority_role(authority_id, role_id) values(?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        for(String id : authorityIds) {
            stmt.setString(1, id);
            stmt.setString(2, roleId);
            stmt.addBatch();
        }
        int[] results = stmt.executeBatch();
    }

}
