package com.gs.authority.util;

/**
 * Created by WangGenshen on 12/2/15.
 */
public class AuthorityUtil {

    public static String getAction(Class clazz, String method) {
        return clazz.getName() + "." + method;
    }

}
