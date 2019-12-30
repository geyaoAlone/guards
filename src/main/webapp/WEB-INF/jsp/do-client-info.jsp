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
    <title></title>
    <script>
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        layui.use('form', function(){
            var form = layui.form;

            //监听提交
            form.on('submit(formDemo)', function(data){

                console.info(data.field)
                console.info(ajaxInvoke('/manage/save/client',data.field));
                return false;
            });
        });
    </script>
    <style>
        form{
            width: 90%;
            margin-top: 10px;
        }
        .input-nolabel{
            margin-left: 47px;
            width: 90%;
        }
        .no_function{
            display: inline-block;
            line-height: 36px;
            color: darkorange;
        }
    </style>
</head>
<body>
<form class="layui-form" action="">

    <div class="layui-form-item">
       <input value="测试客户端" type="text" name="clientName" required  lay-verify="required" placeholder="请输入客户端名称" autocomplete="off" class="layui-input input-nolabel">
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">https://</label>
        <div class="layui-input-block">
            <input value="120.0.0.1:8888/service" type="text" name="serviceUrl" required  lay-verify="required" placeholder="请输入服务地址" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item layui-form-text">
        <textarea name="interfaceUrl" placeholder="请输入JSON格式的url集" class="layui-textarea input-nolabel">{"查询数据":"json/queryAllList","删除数据":"manage/delete"}
        </textarea>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">可用功能</label>
        <div class="layui-input-block">
            <c:if test="${empty functionList}"><span class="no_function">暂无</span></c:if>
            <c:forEach items="${functionList}" var="item">
                <input type="checkbox" name="useFunction" title="${item.functionName}" value="${item.functionNo}">
            </c:forEach>
        </div>
    </div>

    <div class="layui-form-item layui-form-text">
        <textarea name="remark" placeholder="请输入备注信息" class="layui-textarea input-nolabel"></textarea>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">开关</label>
        <div class="layui-input-block">
            <input type="checkbox" name="serviceSwitch" lay-skin="switch">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="formDemo">提交</button>
            <button type="button" class="layui-btn layui-btn-primary">退出</button>
        </div>
    </div>
</form>
</body>
</html>
