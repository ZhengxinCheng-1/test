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
        .payBtn{
            background-color: dodgerblue;
            color: white;
            border-color: white;
        }

        .delBtn{
            background-color: red;
            color: white;
            border-color: antiquewhite;
        }


    </style>
</head>
<body>
<div class="root">
    <div class="header">
        <form action="orders" method="post" id="h-form">
            <input type="hidden" name="method" value="list">
            <input type="hidden" name="status" value="1">
            <table>
                <td><input type="text" name="orderNum" value="${param.orderNum}" placeholder="orderNum"></td>
                <td><input type="date" name="date" value="${param.date}"></td>
                <td><button type="button" id="h-button">search</button></td>
            </table>
        </form>
    </div>

    <div class="cont">
        <div class="list">
            <table class="gridtable">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>OrderNum</th>
                        <th>DeviceName</th>
                        <th>Price</th>
                        <th>Count</th>
                        <th>Amount</th>
                        <th>CreateDate</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${ordersList}" var="order" varStatus="vs">
                        <tr>
                            <td>${vs.count}</td>
                            <td data-val="${order.orderNum}">${order.orderNum}</td>
                            <td data-val="${order.device.deviceName}">${order.device.deviceName}</td>
                            <td data-val="${order.price}">${order.price}</td>
                            <td data-val="${order.count}">${order.count}</td>
                            <td data-val="${order.amount}">${order.amount}</td>
                            <td data-val="${order.createDate}"><fmt:formatDate value='${order.createDate}' pattern='yyyy-MM-dd hh:mm:ss' /></td>
                            <td>submission</td>
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

    function reloadTab() {
        $('#h-form')[0].submit()
    }


</script>
</body>
</html>
