<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>$Title$</title>
    <script src="<%=request.getContextPath()%>/static/js/jquery-3.5.1.js"></script>

    <style>
        .root{
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100%;
        }

        .wrap{
            padding: 20px;
            border-radius: 10px;
            border: 1px solid lightgray;
            background-color: white;
        }

        .title{
            font-size: 30px;
            font-weight: 600;
            text-align: center;
            padding: 10px;
            color: #606266;
        }

        form{
            width: 400px;
        }

        table{
            width: 100%;
            font-size: 16px;
        }

        table tr td:nth-child(1){
            width: 100px;
            padding: 10px;
        }

        tr input{
            width: 100%;
            padding: 5px;
        }

        .btn{
            text-align: center;
            margin: 10px 0;
        }
        .btn button{
            width: 100px;
            height: 30px;
            font-size: 18px;
        }
        .msg{
            text-align: center;
            color: orangered;
            font-size: 16px;
        }

        #del{
            background-color: orangered;
            color: white;
            border-color: white;
        }

    </style>
</head>
<body>
<div class="root">
    <div class="wrap">
        <div class="title">User Info</div>
        <form action="user" method="post" id="infoForm">
            <input type="hidden" name="method" value="update">
            <input type="hidden" name="id" value="${sysUser.id}">
            <table>
                <tr>
                    <td>Username</td>
                    <td><input name = "username" type="text" placeholder="full name/email" value="${sysUser.username}"></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="password" placeholder="password" value="${sysUser.password}"></td>
                </tr>
                <tr>
                    <td>Phone</td>
                    <td><input type="text" name="phone" placeholder="phone" value="${sysUser.phone}"></td>
                </tr>
                <tr>
                    <td>Role</td>
                    <td>
                        <c:if test="${sysUser.roleType==1}">Admin</c:if>
                        <c:if test="${sysUser.roleType==2}">Staff User</c:if>
                        <c:if test="${sysUser.roleType==3}">Customer</c:if>
                    </td>
                </tr>
            </table>
            <div class="btn">
                <button type="button" id="submitBtn">update</button>
                <c:if test="${sysUser.roleType!=1}">
                    <button type="button" id="del">cancel</button>
                </c:if>
            </div>
            <div class="msg">${requestScope.errMsg}</div>
        </form>
    </div>
</div>

    <script type="text/javascript">
    $("#submitBtn").click(function () {
        var useruame = $("input[name='username']").val();
        var password = $("input[name='password']").val();
        var phone = $("input[name='phone']").val();
        if (useruame == undefined || useruame.trim() == "") {
            alert("Please enter a username!");
            return false;
        }
        if (password == undefined || password.trim() == "") {
            alert("Please enter a password!");
            return false;
        }
        if (phone == undefined || phone.trim() == "") {
            alert("Please enter a phone!");
            return false;
        }

        $('#infoForm')[0].submit()
    })

    $("#del").click(function () {
        var r = confirm("Are you sure to cancel your account?")
        if(r == true){
            $.get('sys?method=cancel',function () {
                window.parent.location.href = "index.jsp"
            })
        }
    })
</script>
</body>
</html>

