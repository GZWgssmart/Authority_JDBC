package com.gs.authority.service;

import com.gs.authority.bean.User;
import com.gs.authority.dao.UserDAO;

/**
 * Created by WangGenshen on 12/2/15.
 */
public class UserService {

    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public User add(User user) {
        return userDAO.add(user);
    }
    
    public User queryByIdWithRoles(String userId) {
        return userDAO.queryByIdWithRoles(userId);
    }

}
