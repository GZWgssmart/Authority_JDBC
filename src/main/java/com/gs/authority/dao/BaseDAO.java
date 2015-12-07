package com.gs.authority.dao;

import com.gs.authority.bean.Pager;

import java.sql.*;
import java.util.List;

/**
 * Created by WangGenshen on 12/2/15.
 */
public abstract class BaseDAO<T> {

    private Connection conn;

    public Connection openConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        final String url = "jdbc:mysql://localhost/authority?useUnicode=true&characterEncoding=utf8";
        final String user = "root";
        final String password = "root";
        conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public abstract T add(T t) throws SQLException;

    public abstract boolean delete(T t) throws SQLException;

    public abstract boolean inactive(T t) throws SQLException;

    public abstract boolean active(T t) throws SQLException;

    public abstract T queryById(Object id) throws SQLException;

    public abstract List<T> queryAll() throws SQLException;

    /**
     * 根据分页对象（主要是pageNo和pageSize）来获取Module对象
     * 把获取到的所有对象存储到Pager的objects中
     *
     * @param pager
     * @return
     */
    public abstract Pager<T> queryByPager(Pager pager) throws SQLException;

    /**
     * 根据SQL语句查询总条数
     * @param sql
     * @return
     * @throws SQLException
     */
    public int count(String sql) throws SQLException {
        int count = 0;
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()) {
            count = rs.getInt(1);
        }
        rs.close();
        stmt.close();
        return count;
    }

}
