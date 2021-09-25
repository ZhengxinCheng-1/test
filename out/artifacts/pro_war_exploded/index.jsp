<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Title</title>
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
      .sginUp{
        text-align: right;
        margin: 10px;
      }
      .sginUp a{
        font-size: 14px;
        color: #909399;
      }

    </style>
  </head>
  <body>
    <div class="root">
      <div class="wrap">
        <div class="title">Login</div>
        <form action="user" method="post" id="loginForm">
          <input type="hidden" name="method" value="login">
          <table>
            <tr>
              <td>Username</td>
              <td><input name = "username" type="text" value="${param.username}" placeholder="full name/email"></td>
            </tr>
            <tr>
              <td>Password</td>
              <td><input type="password" name="password" value="${param.password}" placeholder="password"></td>
            </tr>
          </table>
          <div class="btn">
            <button type="button" id="submitBtn">Sgin in</button>
          </div>
          <div class="sginUp">
            <a href="register.jsp">Create your account</a>
          </div>
          <div class="msg">${requestScope.errMsg}</div>
        </form>
      </div>
    </div>

    <script type="text/javascript">
        $("#submitBtn").click(function () {
            var useruame = $("input[name='username']").val();
            var password = $("input[name='password']").val();
            if (useruame == undefined || useruame.trim() == "") {
                alert("Please enter a username!");
                return false;
            }
            if (password == undefined || password.trim() == "") {
                alert("Please enter a password!");
                return false;
            }
            $('#loginForm')[0].submit()
        })
    </script>
  </body>
</html>
