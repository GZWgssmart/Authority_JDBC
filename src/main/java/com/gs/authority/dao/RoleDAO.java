package com.gs.authority.dao;

import com.gs.authority.bean.Module;
import com.gs.authority.bean.Pager;
import com.gs.authority.bean.Role;
import com.gs.authority.bean.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by WangGenshen on 12/2/15.
 */
public class RoleDAO extends BaseDAO<Role> {

    private Connection conn;

    public RoleDAO() throws SQLException, ClassNotFoundException {
        conn = openConnection();
    }

    @Override
    public Role add(Role role) throws SQLException {
        String sql = "insert into t_role(id, name, all_authority) values(?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, role.getId());
        stmt.setString(2, role.getName());
        stmt.setInt(3, role.isAllAuthority() ? 1 : 0);
        stmt.execute();
        int updateCount = stmt.getUpdateCount();
        stmt.close();
        closeConnection();
        return updateCount == 1 ? role : null;
    }

    @Override
    public boolean delete(Role role) throws SQLException {
        return false;
    }

    @Override
    public boolean inactive(Role role) throws SQLException {
        return false;
    }

    @Override
    public boolean active(Role role) throws SQLException {
        return false;
    }

    @Override
    public Role queryById(Object id) throws SQLException {
        return null;
    }

    @Override
    public List<Role> queryAll() throws SQLException {
        List<Role> roles = new ArrayList<Role>();
        String sql = "select * from t_role";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()) {
            Role role = new Role();
            role.setId(rs.getString("id"));
            role.setName(rs.getString("name"));
            roles.add(role);
        }
        return roles.size() > 0 ? roles : null;
    }

    @Override
    public Pager<Role> queryByPager(Pager pager) throws SQLException {
        String sql_count = "select count(id) as total from t_role";
        String sql = "select r.id, r.name, r.all_authority " +
                "from t_role r limit " + pager.getBeginIndex() + ", " + pager.getPageSize();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            List<Role> roles = new ArrayList<Role>();
            rs.beforeFirst();
            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getString("id"));
                role.setName(rs.getString("name"));
                role.setAllAuthority(rs.getInt("all_authority") == 1 ? true : false);
                roles.add(role);
            }
            pager.setTotalRecords(count(sql_count));
            pager.setObjects(roles);
        }
        rs.close();
        stmt.close();
        closeConnection();
        return pager;
    }

    /**
     * 根据角色id获取角色信息,并且获取该角色下的所有用户
     *
     * @param roleId
     * @return
     */
    public Role queryByIdWithUsers(String roleId) throws SQLException {
        Role role = getRole(roleId);
        if(role != null) {
            role.setUsers(getUsersForRole(roleId));
        }
        closeConnection();
        return role;
    }

    private Role getRole(String roleId) throws SQLException {
        Role role = null;
        String sql = "select * from t_role where id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, roleId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            role = new Role();
            role.setId(rs.getString("id"));
            role.setName(rs.getString("name"));
            role.setAllAuthority(rs.getInt("all_authority") == 1 ? true : false);
        }
        rs.close();
        stmt.close();
        return role;
    }

    private List<User> getUsersForRole(String roleId) throws SQLException {
        List<User> users = null;
        String sql = "select u.id, u.name, u.default_role, u.role_names, " +
                "(select tr.name from t_role tr join u on u.default_role = tr.id) as default_role_name " +
                "from t_user u join t_role_user ru on " +
                "u.id = ru.user_id and ru.role_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, roleId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            users = new ArrayList<User>();
            rs.beforeFirst();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setRoleNames(rs.getString("role_names"));
                Role defaultRole = new Role();
                defaultRole.setId(rs.getString("default_role"));
                defaultRole.setName(rs.getString("default_role_name"));
                user.setDefaultRole(defaultRole);
                users.add(user);
            }
        }
        rs.close();
        stmt.close();
        return users;
    }

}
