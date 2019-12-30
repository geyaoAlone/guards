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
    <style>
        .layui-body{
            left: 20px;
        }
        .layui-layout-admin .layui-footer{
            left: 0px;
        }
        .func-btn{
            float: right;
            margin: 15px 25px 10px 0px;
        }

    </style>
    <script>
        var layer;
        layui.use('layer', function(){
            layer = layui.layer;


        });
        function createClient() {
            layer.open(
                {
                    type:2,
                    title:'添加客户端',
                    content:'/manage/open/client',
                    area:['550px', '600px']
                }
            )
        }
        function closeLayer(index) {
            layer.close(index);
            title1();
        }

    </script>
</head>
<body class="layui-layout-body">
    <div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">管理系统</div>
        <ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item layui-this"><a href="javascript:void(0)" onclick="title1()">客户端管理</a></li>
            <li class="layui-nav-item"><a href="javascript:void(0)" onclick="title2()">提供功能管理</a></li>
            <li class="layui-nav-item"><a href="javascript:void(0)" onclick="title3()">请求日志</a></li>
            <span class="layui-nav-bar" style="left: 266px; top: 55px; width: 0px; opacity: 0;"></span></ul>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item"><a href="javascript:void(0)" id="logout">退出登陆</a></li>
            <span class="layui-nav-bar"></span></ul>
    </div>

    <div class="layui-body">
        <div class="func-btn">
            <button type="button" class="layui-btn" onclick="createClient()">配置客户端</button>
        </div>

        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>所有客户端</legend>
        </fieldset>
        <table class="layui-table" lay-even="" lay-skin="row">
            <colgroup>
                <col width="100">
                <col width="150">
                <col width="200">
                <col width="30">
                <col width="250">
                <col width="250">
                <col width="150">
                <col width="150">
                <col>
            </colgroup>
            <thead>
            <tr>
                <th>编号</th>
                <th>名称</th>
                <th>服务地址</th>
                <th>开关</th>
                <th>url集</th>
                <th>可用功能</th>
                <th>密钥</th>
                <th>操作时间</th>
                <th>备注</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${data}" var="item">
                <tr>
                    <td>${item.clientNo}</td>
                    <td>${item.clientName}</td>
                    <td>${item.serviceUrl}</td>
                    <td>${item.serviceSwitch}</td>
                    <td>${item.interfaceUrl}</td>
                    <td>${item.useFunction}</td>
                    <td>${item.secretKey}</td>
                    <td>${item.doTime}</td>
                    <td>${item.remark}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>

    <div class="layui-footer">
        底部固定区域
    </div>
</div>
</body>
</html>
