<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

    </style>
</head>
<body>
<div class="root">
    <div class="header">
        <form action="log?method=list" method="post" id="h-form">
            <table>
                <td><input type="date" name="date" value="${param.date}" placeholder="date"></td>
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
                        <th>Action</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${logs}" var="log">
                        <tr>
                            <td>${sysUser.username}</td>
                            <td>${log.action}</td>
                            <td><fmt:formatDate value='${log.createDate}' pattern='yyyy-MM-dd hh:mm:ss' /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

    </div>
</div>

<script type="text/javascript">
    $("#h-button").click(function () {
        $('#h-form')[0].submit()
    })

</script>
</body>
</html>
