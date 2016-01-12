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
    <title>角色管理</title>
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
        $(function () {
            setPagination("#role_list")
        });

        function formatterAllAuthority(value, rec) {
            return rec.allAuthority ? "是" : "否";
        }

        function toAddRole() {
            $("#addRole").window("open");
        }

        function addRole() {
            $.post("<%=path %>/roleAction/add",
                    $("#addRoleForm").serialize(),
                    function (data) {
                        if (data.result == "ok") {
                            $("#addRole").window("close");
                            $("#role_list").datagrid("reload");
                        } else {
                            $.messager.alert("提示", data.errMsg, "info");
                        }
                    }
            );
        }

        function showAuthorities() {
            var row = $("#role_list").datagrid("getSelected");
            if (row) {
                addTab("权限列表：" + row.name, "<%=path %>/roleAction/roleAuthority?roleId=" + row.id + "&roleName=" + row.name);
            } else {
                $.messager.alert("提示", "请选择需要显示权限列表的角色", "info");
            }

        }
    </script>
</head>
<body style="margin:0;padding:0;">
<div id="operations" class="easyui-tabs" style="width:100%;height:100%;" data-options="border:false">
    <div title="角色列表">
        <table id="role_list" class="easyui-datagrid" toolbar="#role_tb" style="height:100%;"
               data-options="
        url:'<%=path %>/roleAction/listPager',
        method:'get',
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				border:false,
				pageSize:20">
            <thead>
            <tr>
                <th field="id" checkbox="true" width="50">角色ID</th>
                <th field="name" width="150">角色名称</th>
                <th field="allAuthority" width="100" formatter="formatterAllAuthority">具有所有权限</th>
            </tr>
            </thead>
        </table>
        <div id="role_tb">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
               onclick="toAddRole();">添加</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cut" plain="true"
               onclick="javascript:alert('Cut')">Cut</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" plain="true"
               onclick="showAuthorities();">权限列表</a>
        </div>

        <div class="easyui-window" title="添加角色" id="addRole" resizable="false" style="width:400px; height:200px;"
             mode="true"
             closed="true">
            <form id="addRoleForm">
                <table>
                    <tr>
                        <td>角色名称:</td>
                        <td><input type="text" name="name" class="easyui-textbox"/></td>
                    </tr>
                    <tr>
                        <td>具有所有权限:</td>
                        <td>
                            <input type="radio" name="allAuthority" value="yes"/>是&nbsp;&nbsp;
                            <input type="radio" name="allAuthority" value="no" checked/>否
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <button type="button" onclick="addRole();">确认</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>

    </div>
</div>
</body>
</html>
