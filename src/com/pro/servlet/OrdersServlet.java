package com.pro.servlet;

import com.pro.model.Device;
import com.pro.model.Orders;
import com.pro.model.SysUser;
import com.pro.service.DeviceService;
import com.pro.service.OrdersService;
import com.pro.utils.Result;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {

    private OrdersService ordersService = new OrdersService();

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
            case "delete":
                delete(req,resp);
                break;
        }
    }

    private void save(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Orders orders = new Orders();
        orders.setAmount(new BigDecimal(req.getParameter("amount")));
        orders.setPrice(new BigDecimal(req.getParameter("price")));
        orders.setDeviceId(Integer.valueOf(req.getParameter("deviceId")));
        orders.setCustomerId(Integer.valueOf(req.getParameter("customerId")));
        orders.setCount(Integer.valueOf(req.getParameter("count")));

        ordersService.saveOrders(orders);
        ServletOutputStream out=resp.getOutputStream();
        OutputStreamWriter ow=new OutputStreamWriter(out,"utf-8");
        ow.write("success");
        ow.flush();
        ow.close();
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));
        ordersService.deleteOrders(id);
        ServletOutputStream out=resp.getOutputStream();
        OutputStreamWriter ow=new OutputStreamWriter(out,"utf-8");
        ow.write("success");
        ow.flush();
        ow.close();
    }


    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Orders orders = new Orders();
        orders.setId(Integer.valueOf(req.getParameter("id")));
        orders.setCount(Integer.valueOf(req.getParameter("count")));
        orders.setAmount(new BigDecimal(req.getParameter("amount")));

        Result result = ordersService.updateOrders(orders);
        ServletOutputStream out=resp.getOutputStream();
        OutputStreamWriter ow=new OutputStreamWriter(out,"utf-8");
        if(result.getCode() == 200){
            ow.write("success");
        }else{
            ow.write(result.getMsg());
        }
        ow.flush();
        ow.close();
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderNum = req.getParameter("orderNum");
        String date = req.getParameter("date");
        Integer status = Integer.valueOf(req.getParameter("status"));
        SysUser sysUser = (SysUser)(req.getSession().getAttribute("sysUser"));

        List<Orders> ordersList = ordersService.getOrdersList(orderNum,status,sysUser.getId(),date);
        req.setAttribute("ordersList",ordersList);
        String path = status == 0 ? "savedOrder.jsp" : "historyOrder.jsp";
        req.getRequestDispatcher(path).forward(req,resp);
    }

}
