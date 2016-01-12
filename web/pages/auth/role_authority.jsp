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
        $(function() {
            setPagination("#role_authority_list")
        });

        function formatterModuleName(value, rec) {
            return rec.module.name;
        }

        function toAddRole() {
            $('#authorities').tree({
                url:"<%=path %>/authorityAction/queryByRoleId?roleId=${roleId }"
            });
            $('#addAuthorityForRole').window("open");
        }

        function addAuthority() {
            var nodes = $('#authorities').tree('getChecked');
            var ids = '';
            for(var i=0; i<nodes.length; i++){
                var id = nodes[i].id;
                if(id.indexOf("====M") < 0) {
                    if (ids != '') {
                        ids += ',';
                    }
                    ids += nodes[i].id;
                }
            }
            if(ids != '') {
                $.post("<%=path %>/authorityAction/addAuthForRole",
                        {"roleId":"${roleId }","ids":ids},
                        function(data) {
                            if(data.result == "ok") {
                                $.messager.alert("提示", "添加权限成功", "info");
                                $('#authorities').tree({
                                    url:"<%=path %>/authorityAction/queryByRoleId?roleId=${roleId }"
                                });
                                $('#role_authority_list').datagrid('reload');
                            } else {
                                $.messager.alert("提示", data.errMsg, "info");
                            }
                        }, "json");
            } else {
                $.messager.alert("提示", "未选择或没有可添加的权限", "info");
            }
        }
    </script>
</head>
<body style="margin:0;padding:0;">
        <table id="role_authority_list" class="easyui-datagrid" toolbar="#role_tb" style="height:100%;"
               data-options="
                url:'<%=path %>/authorityAction/listPagerByRole?roleId=${roleId}',
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
                <th field="moduleName" width="100" formatter="formatterModuleName">模块名称</th>
            </tr>
            </thead>
        </table>
        <div id="role_tb">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
               onclick="toAddRole();">增加权限</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cut" plain="true"
               onclick="javascript:alert('Cut')">移除权限</a>
        </div>

        <div class="easyui-window" title="添加权限" id="addAuthorityForRole" resizable="false" style="width:400px; height:400px;"
             mode="true"
             closed="true">
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="addAuthority();">添加所选权限</a>
            <ul id="authorities" class="easyui-tree" data-options="method:'get',animate:true,checkbox:true"></ul>
        </div>

</body>
</html>
