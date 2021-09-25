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


        .pop,.pop-order{
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


    </style>
</head>
<body>
<div class="root">
    <div class="header">
        <form action="device?method=list" method="post" id="h-form">
            <table>
                <td><input type="text" name="deviceName" value="${param.deviceName}" placeholder="deviceName"></td>
                <td><input type="text" name="deviceType" value="${param.deviceType}" placeholder="deviceType"></td>
                <td><button type="button" id="h-button">search</button></td>
                <td><button type="button" id="h-button-add">add</button></td>
            </table>
        </form>
    </div>

    <div class="cont">
        <div class="list">
            <table class="gridtable">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>DeviceName</th>
                        <th>DeviceType</th>
                        <th>UnitPrice</th>
                        <th>Stock</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${devices}" var="device" varStatus="vs">
                        <tr>
                            <td>${vs.count}</td>
                            <td data-val="${device.deviceName}">${device.deviceName}</td>
                            <td data-val="${device.deviceType}">${device.deviceType}</td>
                            <td data-val="${device.unitPrice}">${device.unitPrice}</td>
                            <td data-val="${device.stock}">${device.stock}</td>
                            <td>
                                <c:if test="${sysUser.roleType ==2 }">
                                    <button class="editBtn" onclick="edit(this,${device.id})">Edit</button>
                                    <button class="delBtn" onclick="del(${device.id})">Del</button>
                                </c:if>
                                <c:if test="${sysUser.roleType ==3 }">
                                    <c:if test="${device.stock == 0}">
                                        <button class="editBtn" disabled onclick="order(this,${device.id})">Order</button>
                                    </c:if>
                                    <c:if test="${device.stock > 0}">
                                        <button class="editBtn" onclick="order(this,${device.id})">Order</button>
                                    </c:if>
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
        <form action="device?method=update" method="post" id="pop-form">
            <input type="hidden" name="method" value="update">
            <input type="hidden" name="id">
            <table>
                <tr>
                    <td>DeviceName</td>
                    <td><input type="text" name = "deviceName" placeholder="deviceName"></td>
                </tr>
                <tr>
                    <td>DeviceType</td>
                    <td><input type="text" name="deviceType" placeholder="deviceType"></td>
                </tr>
                <tr>
                    <td>UnitPrice</td>
                    <td><input type="number" name="unitPrice" placeholder="unitPrice"></td>
                </tr>
                <tr>
                    <td>Stock</td>
                    <td><input type="number" name="stock" placeholder="stock"></td>
                </tr>
            </table>
            <div class="btn">
                <button type="button" id="sure">sure</button>
                <button type="button" id="cancel">cancel</button>
            </div>
        </form>
    </div>
</div>

<div class="pop-order">
    <div class="pop-wrap">
        <form action="orders" method="post" id="order-form">
            <input type="hidden" name="method" value="save">
            <input type="hidden" name="customerId" value="${sysUser.id}">
            <input type="hidden" name="deviceId">
            <table>
                <tr>
                    <td>DeviceName</td>
                    <td><input type="text" name = "deviceName" readonly></td>
                </tr>
                <tr>
                    <td>DeviceType</td>
                    <td><input type="text" name="deviceType" readonly></td>
                </tr>
                <tr>
                    <td>UnitPrice</td>
                    <td><input type="number" name="price" readonly></td>
                </tr>
                <tr>
                    <td>Count</td>
                    <td><input type="number" name="count" placeholder="count" value="0"></td>
                </tr>
                <tr>
                    <td>Amount</td>
                    <td><input type="number" name="amount" readonly value="0"></td>
                </tr>
            </table>
            <div class="btn">
                <button type="button" id="o-sure">sure</button>
                <button type="button" id="o-cancel">cancel</button>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript">
    $("#h-button").click(function () {
        $('#h-form')[0].submit()
    })

    $("#h-button-add").click(function () {
        $('.pop').css('display','flex')
        $('#pop-form')[0].reset()
        $('#pop-form input[name="method"]').val('save')
    })

    $('#sure').click(function () {
        var data = $('#pop-form').serialize();
        console.log(data)
        $.post('device?' + data,function (res) {
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

    $('#o-sure').click(function () {
        var count = $('#order-form input[name="count"]').val();
        if(count*1 == 0){
            alert("The quantity cannot be zero")
            return
        }

        var data = $('#order-form').serialize();
        console.log(data)
        $.post('orders?' + data,function (res) {
            if(res == 'success'){
                reloadTab()
                $('.pop').css('display','none')
            }else{
                alert(res)
            }
        })
    })

    $('#o-cancel').click(function () {
        $('.pop-order').css('display','none')
    })

    $('#order-form input[name="count"]').blur(function () {
        var ipt = $(this);
        var count = ipt.val();

        if(count == ''){
            count = 0;
            ipt.val(count)
        }

        if(count > ipt.attr('max')*1){
            count = ipt.attr('max');
            ipt.val(count)
        }
        console.log(count,ipt.attr('max'))

        var price = $('#order-form input[name="price"]').val()
        $('#order-form input[name="amount"]').val(count*price)

    })

    function order(btn,id) {

        var tds = $(btn).parent().siblings('td')
        var deviceName = tds.eq(1).attr('data-val')
        var deviceType = tds.eq(2).attr('data-val')
        var unitPrice = tds.eq(3).attr('data-val')
        var stock = tds.eq(4).attr('data-val')
        console.log(id,deviceName,deviceType,unitPrice,stock)
        if(stock == '0'){
            alert("The number of device is 0!")
            return
        }

        $('.pop-order').css('display','flex')
        $('#order-form')[0].reset()
        $('#order-form input[name="deviceId"]').val(id)
        $('#order-form input[name="deviceName"]').val(deviceName)
        $('#order-form input[name="deviceType"]').val(deviceType)
        $('#order-form input[name="price"]').val(unitPrice)
        $('#order-form input[name="count"]').attr('max',stock);



    }

    function edit(btn,id) {
        var tds = $(btn).parent().siblings('td')
        var deviceName = tds.eq(1).attr('data-val')
        var deviceType = tds.eq(2).attr('data-val')
        var unitPrice = tds.eq(3).attr('data-val')
        var stock = tds.eq(4).attr('data-val')

        console.log(id,deviceName,deviceType,unitPrice,stock)

        $('#pop-form input[name="id"]').val(id)
        $('#pop-form input[name="method"]').val('update')
        $('#pop-form input[name="deviceName"]').val(deviceName)
        $('#pop-form input[name="deviceType"]').val(deviceType)
        $('#pop-form input[name="unitPrice"]').val(unitPrice)
        $('#pop-form input[name="stock"]').val(stock)

        $('.pop').css('display','flex')
    }

    function del(id) {
        var r = confirm("Are you sure to delete?");
        if(r == true){
            $.post('device',{method:'delete',id:id},function (res) {
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
