<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="<%=request.getContextPath()%>/static/js/jquery-3.5.1.js"></script>
    <style>
        a{
            text-decoration: none;
        }

        body{
            margin: 0;
        }

        .header{
            height: 60px;
            background-color: #C0C4CC;
        }

        .cont-body{
            position: absolute;
            left: 0;
            right: 0;
            top: 65px;
            bottom: 0;
        }

        .menu{
            float: left;
            width: 15%;
            height: 100%;
            background-color: #606266;
        }

        .main{
            float: right;
            width: 85%;
            height: 100%;
        }

        .mu-item{
            text-align: center;
            font-size: 16px;
            padding: 10px;
            background-color: #606266;
        }

        .mu-item:hover{
            background-color: #67C23A;
        }

        .mu-item a{
            color: white;
            display: block;
        }

        .mu-sub{
            background-color: #C0C4CC;
        }

        .nav-right{
            float: right;
            margin-right: 20px;
            height: 100%;
        }

        .nav-right a{
            display: inline-block;
            margin-right: 15px;
            color: white;
            line-height: 60px;
        }

        .nav-right a:hover{
            color: #67C23A;
        }
    </style>
</head>
<body>

    <div>
        <div class="header">
            <div class="nav-right">
                <a target="ifm" href="user?method=info">Hi, ${sysUser.username}</a>
                <a href="sys?method=logout">logout</a>
            </div>
        </div>
        <div class="cont-body">
            <div class="menu">
                <%--admin--%>
                <c:if test="${sysUser.roleType==1}">
                    <div class="mu-item">
                        <a target="ifm" href="user?method=list">User Manage</a>
                    </div>
                </c:if>
                <%--Staff--%>
                <c:if test="${sysUser.roleType==2}">
                    <div class="mu-item">
                        <a target="ifm" href="device?method=list">Device Manage</a>
                    </div>
                </c:if>
                <%--Customer--%>
                <c:if test="${sysUser.roleType==3}">
                    <div class="mu-item">
                        <a target="ifm" href="device?method=list">Device Manage</a>
                    </div>
                    <div class="mu-item mu-list">
                        <a href="javascript:void(0);">Orders Manage</a>
                    </div>
                    <div style="display: none">
                        <div class="mu-item mu-sub">
                            <a target="ifm" href="orders?method=list&status=0">Saved Orders</a>
                        </div>
                        <div class="mu-item mu-sub">
                            <a target="ifm" href="orders?method=list&status=1">History Orders</a>
                        </div>
                    </div>
                    <div class="mu-item mu-list">
                        <a href="javascript:void(0);">Payment Manage</a>
                    </div>
                    <div style="display: none">
                        <div class="mu-item mu-sub">
                            <a target="ifm" href="payment?method=list&status=0">Saved Payment</a>
                        </div>
                        <div class="mu-item mu-sub">
                            <a target="ifm" href="payment?method=list&status=1">History Payment</a>
                        </div>
                    </div>
                </c:if>

                <div class="mu-item">
                    <a target="ifm" href="log?method=list">Logs</a>
                </div>
            </div>
            <div class="main">
                <div>
                    <iframe class="sub_page" name="ifm" runat="server" src="<%=request.getContextPath()%>/log?method=list" width="100%" height="100%"
                            frameborder="no" border="0" marginwidth="0"
                            marginheight="0" scrolling="yes" allowtransparency="yes"></iframe>
                </div>
            </div>
        </div>

    </div>

    <script>
        $('.mu-list').click(function () {
            $(this).next('div').toggle()
        })

    </script>

</body>
</html>
