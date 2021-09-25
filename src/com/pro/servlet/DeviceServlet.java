package com.pro.servlet;

import com.pro.model.Device;
import com.pro.service.DeviceService;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/device")
public class DeviceServlet extends HttpServlet {

    private DeviceService deviceService = new DeviceService();

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
            case "info":
                info(req,resp);
                break;
            case "delete":
                delete(req,resp);
                break;
        }
    }

    private void save(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Device device = new Device();
        device.setDeviceName(req.getParameter("deviceName"));
        device.setDeviceType(req.getParameter("deviceType"));
        device.setUnitPrice(new BigDecimal(req.getParameter("unitPrice")));
        device.setStock(Integer.valueOf(req.getParameter("stock")));

        deviceService.saveDevice(device);
        ServletOutputStream out=resp.getOutputStream();
        OutputStreamWriter ow=new OutputStreamWriter(out,"utf-8");
        ow.write("success");
        ow.flush();
        ow.close();
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));
        deviceService.deleteDevice(id);
        ServletOutputStream out=resp.getOutputStream();
        OutputStreamWriter ow=new OutputStreamWriter(out,"utf-8");
        ow.write("success");
        ow.flush();
        ow.close();
    }

    private void info(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("info.jsp").forward(req,resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Device device = new Device();
        device.setId(Integer.valueOf(req.getParameter("id")));
        device.setDeviceName(req.getParameter("deviceName"));
        device.setDeviceType(req.getParameter("deviceType"));
        device.setUnitPrice(new BigDecimal(req.getParameter("unitPrice")));
        device.setStock(Integer.valueOf(req.getParameter("stock")));

        deviceService.updateDevice(device);
        ServletOutputStream out=resp.getOutputStream();
        OutputStreamWriter ow=new OutputStreamWriter(out,"utf-8");
        ow.write("success");
        ow.flush();
        ow.close();
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String deviceName = req.getParameter("deviceName");
        String deviceType = req.getParameter("deviceType");
        System.out.println("u:" + deviceName);
        System.out.println("p:" + deviceType);

        List<Device> sysUserList = deviceService.getDeviceList(deviceName, deviceType);
        req.setAttribute("devices",sysUserList);
        req.getRequestDispatcher("device.jsp").forward(req,resp);
    }

}
