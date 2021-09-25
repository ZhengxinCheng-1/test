<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
    <script src="<%=request.getContextPath()%>/static/js/jquery-3.5.1.js"></script>

    <style>
        body,form{margin: 0}
        a{text-decoration: none}
        table.gridtable {font-family: verdana,arial,sans-serif;font-size:14px;color:#333333;border-width: 1px;border-color: #666666;border-collapse: collapse;}

        table.gridtable th {border-width: 1px;padding: 8px;border-style: solid;border-color: #666666;background-color: #dedede;}

        table.gridtable td {border-width: 1px;padding: 8px;border-style: solid;border-color: #666666;background-color: #ffffff;}


        .root{

            height: 100%;
        }

        .header input,.header button{
            padding: 8px;
            font-size: 16px;
        }

        .header{
            padding-bottom: 10px;
            margin: 10px;
            border-bottom: 1px solid rgb(235,235,235);
        }

        .cont{
            margin: 10px;
        }


        .pop{
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background-color: rgba(0,0,0,0.2);
            display: none;
            justify-content: center;
            align-items: center;
        }

        .pop-wrap{
            background-color: white;
            padding:10px;
            width: 300px;
        }

        .pop-wrap input{
            padding: 5px;
            margin: 5px;
        }

        .btn{
            text-align: center;
            margin: 10px 0;
        }
        .btn button{
            width: 80px;
            height: 30px;
            font-size: 16px;
            margin-right: 10px;
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
    <div class="header">
        <form action="user?method=list" method="post" id="h-form">
            <table>
                <td><input type="text" name="username" value="${param.username}" placeholder="username or email"></td>
                <td><input type="text" name="phone" value="${param.phone}" placeholder="phone"></td>
                <td><button type="button" id="h-button">search</button></td>
            </table>
        </form>
    </div>

    <div class="cont">
        <div class="list">
            <table class="gridtable">
                <thead>
                    <tr>
                        <th>Username</th>
                        <th>Password</th>
                        <th>Phone</th>
                        <th>Role</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td data-val="${user.username}">${user.username}</td>
                            <td data-val="${user.password}">${user.password}</td>
                            <td data-val="${user.phone}">${user.phone}</td>
                            <td data-val="${user.roleType}">
                                <c:if test="${user.roleType == 1}">Admin</c:if>
                                <c:if test="${user.roleType == 2}">Staff User</c:if>
                                <c:if test="${user.roleType == 3}">Customer</c:if>
                            </td>
                            <td data-val="${user.status}">
                                <c:if test="${user.status==1}">activate</c:if>
                                <c:if test="${user.status==2}">deactivate</c:if>
                            </td>
                            <td>
                                <c:if test="${user.roleType!=1}">
                                    <button class="editBtn" onclick="edit(this,${user.id})">Edit</button>
                                    <button class="delBtn" onclick="del(${user.id})">Del</button>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

    </div>
</div>

<div class="pop">
    <div class="pop-wrap">
        <form action="user?method=update" method="post" id="pop-form">
            <input type="hidden" name="method" value="listUpdate">
            <input type="hidden" name="id">
            <table>
                <tr>
                    <td>Username</td>
                    <td><input name = "username" type="text" placeholder="full name/email"></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="password" placeholder="password"></td>
                </tr>
                <tr>
                    <td>Phone</td>
                    <td><input type="text" name="phone" placeholder="phone"></td>
                </tr>
                <tr>
                    <td>Role</td>
                    <td>
                        Staff User<input type="radio" name="roleType" value="2">
                        Customer<input type="radio" name="roleType" value="3">
                    </td>
                </tr>
                <tr>
                    <td>Status</td>
                    <td>
                        activate<input type="radio" name="status" value="1">
                        deactivate<input type="radio" name="status" value="2">
                    </td>
                </tr>
            </table>
            <div class="btn">
                <button type="button" id="sure">sure</button>
                <button type="button" id="cancel">cancel</button>
            </div>
            <div class="msg">${requestScope.errMsg}</div>
        </form>
    </div>
</div>

<script type="text/javascript">
    $("#h-button").click(function () {
        $('#h-form')[0].submit()
    })

    $('#sure').click(function () {
        var data = $('#pop-form').serialize();
        console.log(data)
        $.post('user?' + data,function (res) {
            if(res == 'success'){
                reloadTab()
                $('.pop').css('display','none')
            }else{
                alert(res)
            }
        })
    })

    $('#cancel').click(function () {
        $('.pop').css('display','none')
    })

    function edit(btn,id) {
        var tds = $(btn).parent().siblings('td')
        var username = tds.eq(0).attr('data-val')
        var password = tds.eq(1).attr('data-val')
        var phone = tds.eq(2).attr('data-val')
        var roleType = tds.eq(3).attr('data-val')
        var status = tds.eq(4).attr('data-val')
        console.log(id,username,password,phone,roleType,status)

        $('#pop-form input[name="id"]').val(id)
        $('#pop-form input[name="username"]').val(username)
        $('#pop-form input[name="password"]').val(password)
        $('#pop-form input[name="phone"]').val(phone)
        $('#pop-form input[name="roleType"][value="'+roleType+'"]').prop("checked", "checked");
        $('#pop-form input[name="status"][value="'+status+'"]').prop("checked", "checked");

        $('.pop').css('display','flex')
    }

    function del(id) {
        var r = confirm("Are you sure to delete?");
        if(r == true){
            $.post('user',{method:'delete',id:id},function (res) {
                if(res == 'success'){
                    reloadTab()
                }
            })
        }
    }

    function reloadTab() {
        $('#h-form')[0].submit()
    }


</script>
</body>
</html>
