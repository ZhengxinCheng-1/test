<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
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

    </style>
  </head>
  <body>
    <div class="root">
      <div class="wrap">
        <div class="title">Create your account</div>
        <form action="user" method="post" id="regForm">
          <input type="hidden" name="method" value="register">
          <table>
            <tr>
              <td>Username</td>
              <td><input name = "username" type="text" placeholder="full name/email" value="${param.username}"></td>
            </tr>
            <tr>
              <td>Password</td>
              <td><input type="password" name="password" placeholder="password" value="${param.password}"></td>
            </tr>
            <tr>
              <td>Phone</td>
              <td><input type="text" name="phone" placeholder="phone" value="${param.phone}"></td>
            </tr>
            <tr>
              <td>Role</td>
              <td>
                Staff<input type="radio" name="roleType" value="2" style="width: 20px" checked>
                Customer<input type="radio" name="roleType" value="3" style="width: 20px;margin-right: 30px">
              </td>
            </tr>
          </table>
          <div class="btn">
            <button type="button" id="submitBtn">Sgin up</button>
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

            $('#regForm')[0].submit()
        })
    </script>
  </body>
</html>
