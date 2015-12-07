<%--
  Created by IntelliJ IDEA.
  User: WangGenshen
  Date: 12/3/15
  Time: 20:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8"/>
    <link rel="stylesheet" href="<%=path %>/js/jquery-easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="<%=path %>/js/jquery-easyui/themes/icon.css"/>
    <link rel="stylesheet" href="<%=path %>/css/site_main.css"/>

    <script src="<%=path %>/js/jquery.min.js"></script>
    <script src="<%=path %>/js/jquery.form.js"></script>
    <script src="<%=path %>/js/jquery-easyui/jquery.easyui.min.js"></script>
    <script src="<%=path %>/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
    <script src="<%=path %>/js/site_main.js"></script>

    <script>
        $(function() {
            setPagination("#user_list")
        });

        function addUser() {
            alert("add user");
        }

        function formatterDefaultRole(value, rec) {
            return rec.defaultRole.name;
        }
    </script>
</head>
<body style="margin:0;padding:0;">
<table id="user_list" class="easyui-datagrid" toolbar="#user_tb" style="height:100%;"
       data-options="
        url:'<%=path %>/userAction/listPager',
        method:'get',
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				border:false,
				pageSize:20">
    <thead>
    <tr>
        <th field="id" checkbox="true" width="50">用户ID</th>
        <th field="name" width="150">用户名称</th>
        <th field="defaultRole" width="100" formatter="formatterDefaultRole">默认角色</th>
        <th field="roleNames" width="200">用户角色</th>
    </tr>
    </thead>
</table>
<div id="user_tb">
    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
       onclick="addUser();">添加</a>
    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cut" plain="true"
       onclick="javascript:alert('Cut')">Cut</a>
    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" plain="true"
       onclick="javascript:alert('Save')">Save</a>
</div>
</body>
</html>
