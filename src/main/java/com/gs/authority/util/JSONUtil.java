package com.gs.authority.util;

/**
 * Created by WangGenshen on 12/3/15.
 */
public class JSONUtil {

    public static String errResult(String errMsg) {
        return "{\"result\":\"error\",\"errMsg\":\"" + errMsg + "\"}";
    }

    public static String result() {
        return "{\"result\":\"ok\"}";
    }

}
