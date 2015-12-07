package com.gs.authority.web;

import com.gs.authority.bean.User;
import com.gs.authority.service.AuthorityService;

/**
 * Created by WangGenshen on 12/7/15.
 */
public class AuthorityFunction {

    public static boolean hasAuthority(User user, String action) {
        AuthorityService authorityService = new AuthorityService();
        return authorityService.hasAuthority(user.getDefaultRole(), action);
    }

}
