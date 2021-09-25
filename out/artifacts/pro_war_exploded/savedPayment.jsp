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
        <form action="payment" method="post" id="h-form">
            <input type="hidden" name="method" value="list">
            <input type="hidden" name="status" value="0">
            <table>
                <td><input type="text" name="payNum" value="${param.payNum}" placeholder="payNum"></td>
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
                        <th>PayNum</th>
                        <th>PayTot</th>
                        <th>Pay Method</th>
                        <th>Credit Card Num</th>
                        <th>Create Date</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${paymentList}" var="payment" varStatus="vs">
                        <tr>
                            <td>${vs.count}</td>
                            <td data-val="${payment.payNum}">${payment.payNum}</td>
                            <td data-val="${payment.payTot}">${payment.payTot}</td>
                            <td data-val="${payment.payMethod}">${payment.payMethod}</td>
                            <td data-val="${payment.cardNum}">${payment.cardNum}</td>
                            <td data-val="${payment.createDate}"><fmt:formatDate value='${payment.createDate}' pattern='yyyy-MM-dd hh:mm:ss' /></td>
                            <td>saved</td>
                            <td>
                                <button class="editBtn" onclick="edit(this,${payment.id})">Edit</button>
                                <button class="payBtn" onclick="commit(${payment.id})">Commit</button>
                                <button class="delBtn" onclick="del(${payment.id})">Del</button>
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
        <form action="payment" method="post" id="pop-form">
            <input type="hidden" name="method" value="update">
            <input type="hidden" name="id">
            <table>
                <tr>
                    <td>PayNum</td>
                    <td><input type="text"  name = "payNum" readonly></td>
                </tr>
                <tr>
                    <td>PayTot</td>
                    <td><input type="text" name = "payTot" readonly></td>
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
                <button type="button" id="sure">sure</button>
                <button type="button" id="cancel">cancel</button>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript">
    $("#h-button").click(function () {
        $('#h-form')[0].submit()
    })

    $('#payMethod').change(function () {
        var data =  $(this).val();
        if(data == 'credit card'){
            $('.card').css('display','')
            $('#pop-form input[name="cardNum"]').attr('required','required')
        }else{
            $('.card').css('display','none')
            $('#pop-form input[name="cardNum"]').removeAttr('required')
        }
        console.log(data)
    });

    $('#sure').click(function () {
        var card = $('#pop-form input[name="cardNum"]')
        if(card.attr('required') && card.val() == ''){
            alert("Please enter credit card number")
            return
        }

        var data = $('#pop-form').serialize();
        console.log(data)
        $.post('payment?' + data,function (res) {
            if(res == 'success'){
                reloadTab()
                $('.pop').css('display','none')
            }else{
                alert(res)
            }
        })
    })

    $('#p-cancel').click(function () {
        $('.pop').css('display','none')
    })

    function edit(btn,id) {
        var tds = $(btn).parent().siblings('td')
        var payNum = tds.eq(1).attr('data-val')
        var payTot = tds.eq(2).attr('data-val')
        var payMethod = tds.eq(3).attr('data-val')
        var cardNum = tds.eq(4).attr('data-val')

        $('#pop-form input[name="id"]').val(id)
        $('#pop-form input[name="payNum"]').val(payNum)
        $('#pop-form input[name="payTot"]').val(payTot)
        $('#pop-form select').val(payMethod)
        if(payMethod == 'credit card'){
            $('.card').css('display','')
            $('#pop-form input[name="cardNum"]').attr('required','required').val(cardNum)
        }

        $('.pop').css('display','flex')
    }

    function del(id) {
        var r = confirm("Are you sure to delete?");
        if(r == true){
            $.post('payment',{method:'delete',id:id},function (res) {
                if(res == 'success'){
                    reloadTab()
                }
            })
        }
    }

    function commit(id) {
       $.post('payment?method=commit&id=' + id,{},function (res) {
           if(res == 'success'){
               reloadTab()
           }
       })
    }

    function reloadTab() {
        $('#h-form')[0].submit()
    }


</script>
</body>
</html>
