<%--
  Created by IntelliJ IDEA.
  User: WangGenshen
  Date: 12/7/15
  Time: 17:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <title>权限管理</title>
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
            setPagination("#authority_list");
        });

        function toAddAuthority() {
            $("#addAuthority").window("open");
        }

        function addAuthority() {
            $.post("<%=path %>/authorityAction/add",
                    $("#addAuthorityForm").serialize(),
                    function (data) {
                        if (data.result == "ok") {
                            $("#addAuthority").window("close");
                            $("#authority_list").datagrid("reload");
                        } else {
                            $.messager.alert("提示", data.errMsg, "info");
                        }
                    }
            );
        }

        function formatterModule(value, rec) {
            return rec.module.name;
        }

        $(function() {
            $('#modules').combobox({
                url:'<%=path %>/moduleAction/all',
                method:'get',
                valueField:'id',
                textField:'text'
            });
        });
    </script>
</head>
<body style="margin:0;padding:0;">
<table id="authority_list" class="easyui-datagrid" toolbar="#authority_tb" style="height:100%;"
       data-options="
        url:'<%=path %>/authorityAction/listPager',
        method:'get',
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				border:false,
				pageSize:20">
    <thead>
    <tr>
        <th field="id" checkbox="true" width="50">权限ID</th>
        <th field="name" width="150">权限名称</th>
        <th field="action" width="300">权限动作</th>
        <th field="module" width="100" formatter="formatterModule">模块名称</th>
    </tr>
    </thead>
</table>
<div id="authority_tb">
    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
       onclick="toAddAuthority();">添加</a>
    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cut" plain="true"
       onclick="javascript:alert('Cut')">Cut</a>
    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" plain="true"
       onclick="javascript:alert('Save')">Save</a>
</div>

<div class="easyui-window" title="添加权限" id="addAuthority" resizable="false" style="width:400px; height:200px;" mode="true"
     closed="true">
    <form id="addAuthorityForm">
        <table>
            <tr>
                <td>权限名称:</td>
                <td><input type="text" name="name" class="easyui-textbox"/></td>
            </tr>
            <tr>
                <td>权限动作:</td>
                <td><input type="text" name="action" class="easyui-textbox" style="width:200px;"/></td>
            </tr>
            <tr>
                <td>模块:</td>
                <td>
                    <select id="modules" class="easyui-combobox" name="module" style="width:200px;">
                        <option value="请选择" selected>请选择</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <button type="button" onclick="addAuthority();">确认</button>
                </td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>

