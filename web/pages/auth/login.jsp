<%--
  Created by IntelliJ IDEA.
  User: WangGenshen
  Date: 12/3/15
  Time: 13:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<!doctype html>
<html>
<head>
    <title>XXX管理系统</title>
    <meta charset="UTF-8" />

    <link rel="stylesheet" href="<%=path %>/js/jquery-easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" href="<%=path %>/css/site_main.css"/>

    <script src="<%=path %>/js/jquery.min.js"></script>
    <script src="<%=path %>/js/jquery.form.js"></script>
    <script src="<%=path %>/js/jquery-easyui/jquery.easyui.min.js"></script>
    <script src="<%=path %>/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>

    <script>
        function login() {
            $.post("<%=path %>/userAction/login",
                    $("#login_form").serialize(),
                    function (data) {
                        var result = data.result;
                        if(result == "ok") {
                            window.location.href = "<%=path %>/userAction/admin";
                        } else {
                            $("#errMsg").html(data.errMsg);
                        }
                    }
            );
        }
    </script>

</head>
<body>
<div class="login_container">
    <img class="login_logo" src="<%=path %>/images/logo.jpg" alt="公司图片" title="公司图片"/>

    <div class="login_div">
        <div class="easyui-panel" title="登录" style="width:240px;padding:10px;">
            <div id="errMsg"></div>
            <form id="login_form">
                <table>
                    <tr>
                        <td>登录名:</td>
                        <td><input type="text" name="name" class="easyui-textbox"/></td>
                    </tr>
                    <tr>
                        <td>密码:</td>
                        <td><input type="password" name="password" class="easyui-textbox"/></td>
                    </tr>
                    <tr>
                        <td>验证码:</td>
                        <td><input type="text" name="code" class="easyui-textbox"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><img src="<%=path %>/captcha" onclick="this.src='<%=path %>/captcha?time=Math.random();'"/>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <button type="button" onclick="login();">登录</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div style="clear:both;"></div>
</div>
</body>
</html>
