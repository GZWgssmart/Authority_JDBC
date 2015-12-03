package com.gs.authority.dao;

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

    public RoleDAO() {
        conn = openConnection();
    }

    @Override
    public Role add(Role role) {
        String sql = "insert into t_role(id, name) values(?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, role.getId());
            stmt.setString(2, role.getName());
            if (stmt.execute()) {
                return role;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return null;
    }

    @Override
    public boolean delete(Role role) {
        return false;
    }

    @Override
    public boolean inactive(Role role) {
        return false;
    }

    @Override
    public boolean active(Role role) {
        return false;
    }

    @Override
    public Role queryById(Object id) {
        return null;
    }

    @Override
    public List<Role> queryAll() {
        return null;
    }

    /**
     * 根据角色id获取角色信息,并且获取该角色下的所有用户
     * @param roleId
     * @return
     */
    public Role queryByIdWithUsers(String roleId) {
        Role  role = new Role();
        List<User> users = new ArrayList<User>();
        String sql = "select r.id as role_id, r.name as role_name, u.id as user_id, u.name as user_name "
                + "from t_role r, t_role_user ru, t_user u "
                + "where r.id = ? and r.id = ru.role_id and ru.user_id = u.id";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, roleId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (role.getId() == null) {
                    role.setId(rs.getString("role_id"));
                    role.setName(rs.getString("role_name"));
                }
                User user = new User();
                user.setId(rs.getString("user_id"));
                user.setName(rs.getString("user_name"));
                users.add(user);
            }
            role.setUsers(users);
            return role;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return null;
    }

}
