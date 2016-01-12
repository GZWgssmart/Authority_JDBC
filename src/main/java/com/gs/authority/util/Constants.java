package com.gs.authority.util;

/**
 * Created by WangGenshen on 12/4/15.
 */
public class Constants {

    public static final String SESSION_CURRENT_USER = "currentUser";

    public static class ModuleAction {
        public static final String LIST = "list";
        public static final String LIST_PAGER = "listPager";
        public static final String ADD = "add";
        public static final String ALL = "all";
    }

    public static class AuthorityAction {
        public static final String LIST = "list";
        public static final String LIST_PAGER = "listPager";
        public static final String ADD = "add";
        public static final String LIST_PAGER_ROLE = "listPagerByRole";
        public static final String QUERY_BY_ROLE_ID = "queryByRoleId";
        public static final String ADD_AUTHORITY_FOR_ROLE = "addAuthForRole";
    }

    public static class RoleAction {
        public static final String LIST = "list";
        public static final String LIST_PAGER = "listPager";
        public static final String ADD = "add";
        public static final String ALL = "all";
        public static final String ROLE_AUTHORITY = "roleAuthority";
    }

    public static class UserAction {
        public static final String LOGIN = "login";
        public static final String ADMIN = "admin";
        public static final String LOGOUT = "logout";
        public static final String LIST_PAGER = "listPager";
        public static final String LIST = "list";
        public static final String ADD = "add";
        public static final String CHANGE_ROLE = "changeRole";
    }

}
