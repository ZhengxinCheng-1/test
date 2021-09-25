package com.pro.servlet;

import com.pro.model.SysLog;
import com.pro.model.SysUser;
import com.pro.service.SysLogService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/log")
public class LogServlet extends HttpServlet {

    private SysLogService sysLogService = new SysLogService();

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
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        SysUser sysUser = (SysUser) req.getSession().getAttribute("sysUser");
        String date = req.getParameter("date");
        System.out.println(date);
        List<SysLog> logs = sysLogService.getSysLogList(sysUser.getId(), date);
        req.setAttribute("logs",logs);
        req.getRequestDispatcher("log.jsp").forward(req,resp);
    }
}
