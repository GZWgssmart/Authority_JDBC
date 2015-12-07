package com.gs.authority.dao;

import com.gs.authority.bean.Authority;
import com.gs.authority.bean.Pager;
import com.sun.org.apache.xerces.internal.util.AugmentationsImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String sql = "insert into t_authority(id, name, action) values(?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, authority.getId());
        stmt.setString(2, authority.getName());
        stmt.setString(3, authority.getAction());
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
        return null;
    }

    @Override
    public Pager<Authority> queryByPager(Pager pager) throws SQLException {
        return null;
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

}
