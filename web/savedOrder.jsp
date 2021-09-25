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


        .pop,.pop-pay{
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
            <input type="hidden" name="status" value="0">
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
                        <th>Action</th>
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
                            <td>saved</td>
                            <td>
                                <button class="editBtn" onclick="edit(this,${order.id})">Edit</button>
                                <button class="payBtn" onclick="pay(this,${order.id})">Pay</button>
                                <button class="delBtn" onclick="del(${order.id})">Del</button>
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
        <form action="orders" method="post" id="pop-form">
            <input type="hidden" name="method" value="update">
            <input type="hidden" name="id">
            <table>
                <tr>
                    <td>OrderNum</td>
                    <td><input type="text"  name = "orderNum" readonly></td>
                </tr>
                <tr>
                    <td>DeviceName</td>
                    <td><input type="text" name = "deviceName" readonly></td>
                </tr>
                <tr>
                    <td>Price</td>
                    <td><input type="text" name = "price" readonly></td>
                </tr>
                <tr>
                    <td>Count</td>
                    <td><input type="number" name="count" placeholder="count"></td>
                </tr>
                <tr>
                    <td>Amount</td>
                    <td><input type="text" name="amount" readonly></td>
                </tr>
            </table>
            <div class="btn">
                <button type="button" id="sure">sure</button>
                <button type="button" id="cancel">cancel</button>
            </div>
        </form>
    </div>
</div>

<div class="pop-pay">
    <div class="pop-wrap">
        <form action="payment" method="post" id="pay-form">
            <input type="hidden" name="method" value="save">
            <input type="hidden" name="orderId">
            <input type="hidden" name="customerId" value="${sysUser.id}">
            <table>
                <tr>
                    <td>Price</td>
                    <td><input type="text" name = "price" readonly></td>
                </tr>
                <tr>
                    <td>Count</td>
                    <td><input type="number" name="count" readonly></td>
                </tr>
                <tr>
                    <td>PayTot</td>
                    <td><input type="text" name="payTot" readonly></td>
                </tr>
                <tr>
                    <td>Pay Method</td>
                    <td>
                        <select name="payMethod" id="payMethod" style="margin: 5px;padding: 6px">
                            <option value="cash">Cash</option>
                            <option value="credit card">Credit Card</option>
                        </select>
                    </td>
                </tr>
                <tr class="card" style="display: none">
                    <td>Card Num</td>
                    <td><input type="text" name="cardNum" placeholder="credit card number"></td>
                </tr>
            </table>
            <div class="btn">
                <button type="button" id="p-sure">sure</button>
                <button type="button" id="p-cancel">cancel</button>
            </div>
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
        $.post('orders?' + data,function (res) {
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

    $('#pop-form input[name="count"]').blur(function () {
        var ipt = $(this);
        var count = ipt.val();

        if(count == '' || count < 0){
            count = 0;
            ipt.val(count)
        }

        if(count > ipt.attr('max')*1){
            count = ipt.attr('max');
            ipt.val(count)
        }
        console.log(count,ipt.attr('max'))

        var price = $('#pop-form input[name="price"]').val()
        $('#pop-form input[name="amount"]').val(count*price)
    })

    $('#payMethod').change(function () {
        var data =  $(this).val();
        if(data == 'credit card'){
            $('.card').css('display','')
            $('#pay-form input[name="cardNum"]').attr('required','required')
        }else{
            $('.card').css('display','none')
            $('#pay-form input[name="cardNum"]').removeAttr('required')
        }
        console.log(data)
    });

    $('#p-sure').click(function () {
        var card = $('#pay-form input[name="cardNum"]')
        if(card.attr('required') && card.val() == ''){
            alert("Please enter credit card number")
            return
        }

        var data = $('#pay-form').serialize();
        console.log(data)
        $.post('payment?' + data,function (res) {
            if(res == 'success'){
                reloadTab()
                $('.pop-pay').css('display','none')
            }else{
                alert(res)
            }
        })
    })

    $('#p-cancel').click(function () {
        $('.pop-pay').css('display','none')
    })

    function edit(btn,id) {
        var tds = $(btn).parent().siblings('td')
        var orderNum = tds.eq(1).attr('data-val')
        var deviceName = tds.eq(2).attr('data-val')
        var price = tds.eq(3).attr('data-val')
        var count = tds.eq(4).attr('data-val')
        var amount = tds.eq(5).attr('data-val')

        $('#pop-form input[name="id"]').val(id)
        $('#pop-form input[name="orderNum"]').val(orderNum)
        $('#pop-form input[name="deviceName"]').val(deviceName)
        $('#pop-form input[name="price"]').val(price)
        $('#pop-form input[name="count"]').val(count)
        $('#pop-form input[name="payTot"]').val(amount)

        $('.pop').css('display','flex')
    }

    function del(id) {
        var r = confirm("Are you sure to delete?");
        if(r == true){
            $.post('orders',{method:'delete',id:id},function (res) {
                if(res == 'success'){
                    reloadTab()
                }
            })
        }
    }

    function pay(btn,id) {
        var tds = $(btn).parent().siblings('td')
        var price = tds.eq(3).attr('data-val')
        var count = tds.eq(4).attr('data-val')
        var amount = tds.eq(5).attr('data-val')
        $('#pay-form input[name="orderId"]').val(id)
        $('#pay-form input[name="price"]').val(price)
        $('#pay-form input[name="count"]').val(count)
        $('#pay-form input[name="payTot"]').val(amount)

        $('.pop-pay').css('display','flex')
    }

    function reloadTab() {
        $('#h-form')[0].submit()
    }


</script>
</body>
</html>
