package com.gs.authority.dao;

import com.gs.authority.bean.Pager;
import com.gs.authority.bean.Role;
import com.gs.authority.bean.User;
import com.gs.authority.util.EncryptUtil;

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

    public UserDAO() throws SQLException, ClassNotFoundException {
        conn = openConnection();
    }

    @Override
    public User add(User user) throws SQLException {
        String sql = "insert into t_user(id, name, password, default_role, role_names) values(?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, user.getId());
        stmt.setString(2, user.getName());
        stmt.setString(3, EncryptUtil.md5Encrypt(user.getPassword()));
        stmt.setString(4, user.getDefaultRole().getId());
        stmt.setString(5, user.getRoleNames());
        stmt.execute();
        int updateCount = stmt.getUpdateCount();
        return updateCount == 1 ? user : null;
    }

    public boolean addRolesForUser(User user) throws SQLException {
        String sql = "insert into t_role_user(role_id, user_id) values(?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        List<Role> roles = user.getRoles();
        for(Role role : roles) {
            stmt.setString(1, role.getId());
            stmt.setString(2, user.getId());
            stmt.addBatch();
        }
        int[] results = stmt.executeBatch();
        stmt.close();
        closeConnection();
        for(int result : results) {
            if(result != 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean delete(User user) throws SQLException {
        return false;
    }

    @Override
    public boolean inactive(User user) throws SQLException {
        return false;
    }

    @Override
    public boolean active(User user) throws SQLException {
        return false;
    }

    @Override
    public User queryById(Object id) throws SQLException {
        return null;
    }

    @Override
    public List<User> queryAll() throws SQLException {
        return null;
    }

    @Override
    public Pager<User> queryByPager(Pager pager) throws SQLException {
        String sql_count = "select count(id) as total from t_user";
        String sql = "select u.id, u.name, u.default_role, u.role_names, " +
                "(select tr.name from t_role tr where tr.id = u.default_role) as default_role_name " +
                "from t_user u limit " + pager.getBeginIndex() + ", " + pager.getPageSize();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            List<User> users = new ArrayList<User>();
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
            pager.setTotalRecords(count(sql_count));
            pager.setObjects(users);
        }
        rs.close();
        stmt.close();
        closeConnection();
        return pager;
    }

    /**
     * 根据用户的基本信息:name和password查找是否具有该用户,如果有该用户,也获取此用户的角色列表,并且把角色列表中的第一个
     * 当作是此用户的当前角色
     *
     * @param user
     * @return
     */
    public User queryWithRoles(User user) throws SQLException {
        User theUser = getUser(user);
        if(theUser != null) {
            theUser.setRoles(getRolesForUser(theUser.getId()));
        }
        closeConnection();
        return theUser;
    }

    /**
     * 根据用户id获取用户信息,并且获取该用户的角色列表
     *
     * @param userId
     * @return
     */
    public User queryByIdWithRoles(String userId) throws SQLException {
        User user = getUserById(userId);
        if(user != null) {
            user.setRoles(getRolesForUser(userId));
        }
        closeConnection();
        return user;
    }

    private User getUser(User user) throws SQLException {
        String sql = "select u.id, u.name, u.role_names, u.default_role, r.name as default_role_name " +
                "from t_user u join t_role r " +
                "on u.default_role = r.id where u.name = ? and u.password = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, user.getName());
        stmt.setString(2, EncryptUtil.md5Encrypt(user.getPassword()));
        ResultSet rs = stmt.executeQuery();
        User theUser = userFromResultSet(rs);
        rs.close();
        stmt.close();
        return theUser;
    }

    private User getUserById(String userId) throws SQLException {
        String sql = "select u.id, u.name, u.role_names, u.default_role, r.name as default_role_name " +
                "from t_user u join t_role r " +
                "on u.default_role = r.id where u.id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, userId);
        ResultSet rs = stmt.executeQuery();
        User user = userFromResultSet(rs);
        rs.close();
        stmt.close();
        return user;
    }

    private User userFromResultSet(ResultSet rs) throws SQLException {
        User user = null;
        if(rs.next()) {
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setRoleNames(rs.getString("role_names"));
            Role defaultRole = new Role();
            defaultRole.setId(rs.getString("default_role"));
            defaultRole.setName(rs.getString("default_role_name"));
            user.setDefaultRole(defaultRole);
        }
        return user;
    }

    private List<Role> getRolesForUser(String userId) throws SQLException {
        List<Role> roles = null;
        String sql = "select r.id, r.name, r.all_authority from t_role r join t_role_user ru on " +
                "r.id = ru.role_id join t_user u on u.id = ru.user_id and u.id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, userId);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()) {
            roles = new ArrayList<Role>();
            rs.beforeFirst();
            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getString("id"));
                role.setName(rs.getString("name"));
                role.setAllAuthority(rs.getInt("all_authority") == 1 ? true : false);
                roles.add(role);
            }
        }
        return roles;
    }

    public boolean updateDefaultRole(User user) throws SQLException {
        String sql = "update t_user set default_role = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, user.getDefaultRole().getId());
        stmt.execute();
        int updateCount = stmt.getUpdateCount();
        return updateCount == 1 ? true : false;
    }

}
