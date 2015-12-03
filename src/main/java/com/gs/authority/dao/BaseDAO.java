package com.gs.authority.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by WangGenshen on 12/2/15.
 */
public abstract class BaseDAO<T> {

    private Connection conn;

    public Connection openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            final String url = "jdbc:mysql://localhost/authority?useUnicode=true&characterEncoding=utf8";
            final String user = "root";
            final String password = "root";
            conn = DriverManager.getConnection(url,user,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConnection() {
        try {
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public abstract T add(T t);

    public abstract boolean delete(T t);

    public abstract boolean inactive(T t);

    public abstract boolean active(T t);

    public abstract T queryById(Object id);

    public abstract List<T> queryAll();

}
