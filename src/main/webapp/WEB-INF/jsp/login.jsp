<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>一个管理系统</title>
    <script src="/static/js/jquery-1.9.1.min.js"></script>
    <script src="/static/js/jsencrypt.min.js"></script>
    <style>
        html,body{
            width:100%;
            height:100%;
            min-height: 700px;
        }
        body{
            font-family: "华文细黑";
            background:url("https://ss3.bdstatic.com/lPoZeXSm1A5BphGlnYG/skin/2.jpg?2") no-repeat;
            background-size: 100%;
        }
        #div_login{
            width:400px;
            min-height: 300px;
            background-color:rgba(58,58,58,0.5);
            margin:0 auto;
            padding: 0px;
            margin-top: 100px;
        }
        #div_title{
            width: 400px;
            height: 60px;
            text-align: center;
            line-height: 60px;
            color:blanchedalmond;
        }
        *{
            margin:0px;
            padding: 0px;
        }
        #midden{
            height: 300px;
            background-color: rgba(77, 75, 75, 0.5);
        }
        .inputinfo{
            width: 80%;
            height:30px;
            margin:8px 40px;
        }
        .submitbutton{
            width: 200px;
            height:40px;
            margin:25px 100px;
            background-color: rgba(89, 89, 89, 0.5)
        }
        #info{
            width:200px;
            text-align: center;
            margin: 0 auto;
        }
    </style>
    <script>
        $(document).ready(function() {

            var key =`${publicKey}`
            $('#submit').on('click', function () {
                var userName = $('#userName').val();
                var password = $('#password').val();
                if (!userName || !password) {
                    alert('你不填用户名或密码就想登陆？');
                    return;
                }
                if(password.length < 8){
                    alert('大佬，密码太短了！');
                    return;
                }
                //密码加密

                var encrypt = new JSEncrypt();
                encrypt.setPublicKey(key);

                var data = {'userName':userName,'password':password};

                var secretData = encrypt.encrypt(JSON.stringify(data));

                $.ajax({
                    url: "/manage/dologin",
                    type: "post",
                    data:{'data':secretData},
                    dateType: "json",
                    cache: false,
                    async: false,
                    success: function (data) {
                        if(data && data.status == '1'){
                            sessionStorage.setItem("publicKey",key);
                            window.location.href = data.object;
                        }else{
                            alert(data.massage ? data.massage:'登录失败')
                        }
                    }
                });
            })
        })
    </script>
</head>
<body>
<div id="div_login">
    <div id="div_title"><h1>登陆</h1></div>
    <div id="midden">
        <p><input class="inputinfo" type="text" name="userName" id="userName" placeholder="请输入用户名"></p>
        <p><input class="inputinfo" type="password" name="password" id="password" placeholder="请输入密码"></p>
        <input type="button" class="submitbutton" value="登陆" id="submit">

        <div id="info">

        </div>
    </div>

</div>
</body>
</html>
