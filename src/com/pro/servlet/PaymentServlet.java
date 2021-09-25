package com.pro.servlet;

import com.pro.model.Payment;
import com.pro.model.SysUser;
import com.pro.service.OrdersService;
import com.pro.service.PaymentService;
import com.pro.utils.Result;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {

    private PaymentService paymentService = new PaymentService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String method = req.getParameter("method");
        switch (method){
            case "list":
                list(req,resp);
                break;
            case "save":
                save(req,resp);
                break;
            case "update":
                update(req,resp);
                break;
            case "commit":
                commit(req,resp);
                break;
            case "delete":
                delete(req,resp);
                break;
        }
    }

    private void commit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));
        Payment payment = new Payment();
        payment.setId(id);
        payment.setStatus(1);
        paymentService.updatePayment(payment);
        ServletOutputStream out=resp.getOutputStream();
        OutputStreamWriter ow=new OutputStreamWriter(out,"utf-8");
        ow.write("success");
        ow.flush();
        ow.close();
    }

    private void save(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Payment payment = new Payment();
        payment.setPayTot(new BigDecimal(req.getParameter("payTot")));
        payment.setPayMethod(req.getParameter("payMethod"));
        payment.setOrderId(Integer.valueOf(req.getParameter("orderId")));
        payment.setCustomerId(Integer.valueOf(req.getParameter("customerId")));
        String cardNum = req.getParameter("cardNum");
        if(cardNum == null){
            cardNum = "";
        }
        payment.setCardNum(cardNum);

        paymentService.savePayment(payment);
        ServletOutputStream out=resp.getOutputStream();
        OutputStreamWriter ow=new OutputStreamWriter(out,"utf-8");
        ow.write("success");
        ow.flush();
        ow.close();
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));
        paymentService.deletePayment(id);
        ServletOutputStream out=resp.getOutputStream();
        OutputStreamWriter ow=new OutputStreamWriter(out,"utf-8");
        ow.write("success");
        ow.flush();
        ow.close();
    }


    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Payment payment = new Payment();
        payment.setId(Integer.valueOf(req.getParameter("id")));
        payment.setPayMethod(req.getParameter("payMethod"));
        String cardNum = req.getParameter("cardNum");
        if(cardNum == null){
            cardNum = "";
        }
        payment.setCardNum(cardNum);

        paymentService.updatePayment(payment);
        ServletOutputStream out=resp.getOutputStream();
        OutputStreamWriter ow=new OutputStreamWriter(out,"utf-8");
        ow.write("success");
        ow.flush();
        ow.close();
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String payNum = req.getParameter("payNum");
        String date = req.getParameter("date");
        Integer status = Integer.valueOf(req.getParameter("status"));
        SysUser sysUser = (SysUser)(req.getSession().getAttribute("sysUser"));

        List<Payment> ordersList = paymentService.getPaymentList(payNum,status,sysUser.getId(),date);
        req.setAttribute("paymentList",ordersList);
        String path = status == 0 ? "savedPayment.jsp" : "historyPayment.jsp";
        req.getRequestDispatcher(path).forward(req,resp);
    }

}
