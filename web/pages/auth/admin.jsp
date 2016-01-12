<%@ page import="com.gs.authority.util.Constants" %>
<%@ page import="com.gs.authority.bean.User" %>
<%@ page import="com.gs.authority.util.AuthorityConstants" %>
<%--
  Created by IntelliJ IDEA.
  User: WangGenshen
  Date: 12/3/15
  Time: 13:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="gs" uri="http://www.gs.com/gs" %>
<%
    String path = request.getContextPath();
%>
<!doctype html>
<html>
<head>
    <title>XXX管理系统</title>
    <meta charset="UTF-8"/>
    <link rel="stylesheet" href="<%=path %>/js/jquery-easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="<%=path %>/css/site_main.css"/>

    <script src="<%=path %>/js/jquery.min.js"></script>
    <script src="<%=path %>/js/jquery.form.js"></script>
    <script src="<%=path %>/js/jquery-easyui/jquery.easyui.min.js"></script>
    <script src="<%=path %>/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
    <script src="<%=path %>/js/site_main.js"></script>
    <script>
        function toChangeRole() {
            $("#changeRole").window("open");
        }

        function changeRole(id, name) {
            $.get("<%=path %>/userAction/changeRole?defaultRoleId=" + id + "&defaultRoleName=" + name, function (data) {
                if(data.result == "ok") {
                    $("#changeRole").window("close");
                    window.location.href = "<%=path %>/userAction/admin";
                }
            }, "json");
        }
    </script>
</head>
<body class="easyui-layout">
<div data-options="region:'north',border:false" style="height:60px;">
    <img class="main_logo" src="<%=path %>/images/logo.jpg"/>

    <div class="wel_msg role">
        欢迎您:<b>${sessionScope.currentUser.name}</b>&nbsp;&nbsp;
        当前角色：<b><a id="currentRole" href="javascript:void(0);" onclick="toChangeRole();">${sessionScope.currentUser.defaultRole.name}</a></b>&nbsp;&nbsp;
        <a href="<%=path %>/userAction/logout">退出</a>
    </div>
</div>
<div data-options="region:'west',split:true,title:'功能菜单'" style="width:200px;padding:10px;">
    <ul class="easyui-tree">
        <li>
            <span>XXX系统</span>
            <ul>
                <li data-options="state:'open'">
                    <span>权限管理</span>
                    <ul>
                        <li>
                            <a href="javascript:void(0);"
                               onclick="addTab('模块管理','<%=path %>/moduleAction/list')">模块管理</a>
                        </li>
                        <li>
                            <a href="javascript:void(0);" onclick="addTab('权限管理','<%=path %>/authorityAction/list')">权限管理</a>
                        </li>
                        <c:if test="${gs:hasAuth(sessionScope.currentUser, AuthorityConstants.ROLE_ADD)}">
                            <li>
                                <a href="javascript:void(0);"
                                   onclick="addTab('角色管理','<%=path %>/roleAction/list')">角色管理</a>
                            </li>
                        </c:if>
                        <li>
                            <a href="javascript:void(0);" onclick="addTab('用户管理','<%=path %>/userAction/list')">用户管理</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </li>
    </ul>
</div>
<div data-options="region:'center'">
    <div id="operations" class="easyui-tabs" style="width:100%;height:100%;" data-options="border:false">
        <div title="主页">
            欢迎登录XXX公司XXX管理系统
        </div>
    </div>
</div>

<div class="easyui-window" title="选择用户当前角色" id="changeRole" resizable="false" style="width:200px; height:100px;" mode="true"
     closed="true">
    <c:forEach items="${sessionScope.currentUser.roles}" varStatus="status" var="role">
        <div class="role">
            <a href="javascript:void(0);" onclick="changeRole('${role.id }', '${role.name}')">${role.name }</a>
        </div>
    </c:forEach>
</div>

</body>
</html>
