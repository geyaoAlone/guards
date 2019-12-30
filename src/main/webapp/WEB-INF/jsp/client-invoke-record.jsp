<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>一个管理系统</title>
    <script src="/static/js/jquery-1.9.1.min.js"></script>
    <script src="/static/js/jsencrypt.min.js"></script>
    <script src="/static/layui/layui.js"></script>
    <link rel="stylesheet" href="/static/layui/css/layui.css">
    <script src="/static/js/commom.js"></script>
    <title>Title</title>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">管理系统</div>
        <ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item "><a href="javascript:void(0)" onclick="title1()">客户端管理</a></li>
            <li class="layui-nav-item"><a href="javascript:void(0)" onclick="title2()">提供功能管理</a></li>
            <li class="layui-nav-item layui-this"><a href="javascript:void(0)" onclick="title3()">请求日志</a></li>
            <span class="layui-nav-bar" style="left: 266px; top: 55px; width: 0px; opacity: 0;"></span></ul>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item"><a href="javascript:void(0)" id="logout">退出登陆</a></li>
            <span class="layui-nav-bar"></span></ul>
    </div>

    <div class="layui-body">
    </div>
<div class="layui-footer">
    底部固定区域
</div>
</body>
</html>
