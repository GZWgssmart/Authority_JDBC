package com.gs.authority.dao;

import com.gs.authority.bean.Role;
import com.gs.authority.bean.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by WangGenshen on 12/2/15.
 */
public class UserDAO extends BaseDAO<User> {

    private Connection conn;

    public UserDAO() {
        conn = openConnection();
    }

    @Override
    public User add(User user) {
        String sql = "insert into t_user(id, name, password) values(?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, UUID.randomUUID().toString());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getPassword());
            if (stmt.execute()) {
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return null;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public boolean inactive(User user) {
        return false;
    }

    @Override
    public boolean active(User user) {
        return false;
    }

    @Override
    public User queryById(Object id) {
        return null;
    }

    @Override
    public List<User> queryAll() {
        return null;
    }

    /**
     * 根据用户的基本信息:name和password查找是否具有该用户,如果有该用户,也获取此用户的角色列表,并且把角色列表中的第一个
     * 当作是此用户的当前角色
     *
     * @param user
     * @return
     */
    public User queryWithRoles(User user) {
        List<Role> roles = new ArrayList<Role>();
        String sql = "select u.id as user_id, u.name as user_name, r.id as role_id, r.name as role_name " +
                "from t_user u, t_role r, t_role_user ru " +
                "where u.id = ru.user_id and u.name = ? and u.password = ? " +
                "group by r.id";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getString("role_id"));
                role.setName(rs.getString("role_name"));
                roles.add(role);
            }
            user.setRoles(roles);
            user.setCurrentRole(roles.size() > 0 ? roles.get(0) : null);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据用户id获取用户信息,并且获取该用户的角色列表
     * @param userId
     * @return
     */
    public User queryByIdWithRoles(String userId) {
        User user = new User();
        List<Role> roles = new ArrayList<Role>();
        String sql = "select u.id as user_id, u.name as user_name, r.id as role_id, r.name as role_name " +
                "from t_user u, t_role r, t_role_user ru " +
                "where u.id = ru.user_id and u.id = ? " +
                "group by r.id";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (user.getId() != null) {
                    user.setId(rs.getString("user_id"));
                    user.setName(rs.getString("user_name"));
                }
                Role role = new Role();
                role.setId(rs.getString("role_id"));
                role.setName(rs.getString("role_name"));
                roles.add(role);
            }
            user.setRoles(roles);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
