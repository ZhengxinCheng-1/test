package com.pro.servlet;

import com.pro.model.SysLog;
import com.pro.model.SysUser;
import com.pro.service.SysLogService;
import com.pro.service.SysUserService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

@WebServlet("/sys")
public class SystemServlet extends HttpServlet {
    private SysLogService logService = new SysLogService();
    private SysUserService sysUserService = new SysUserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String method = req.getParameter("method");
        switch (method){
            case "logout":
                logout(req,resp);
                break;
            case "cancel":
                cancel(req,resp);
                break;
        }
    }

    private void cancel(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        SysUser sysUser = (SysUser) req.getSession().getAttribute("sysUser");
        sysUserService.deleteSysUser(sysUser.getId());
        ServletOutputStream out=resp.getOutputStream();
        OutputStreamWriter ow=new OutputStreamWriter(out,"utf-8");
        ow.write("success");
        ow.flush();
        ow.close();
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        SysUser sysUser = (SysUser) req.getSession().getAttribute("sysUser");

        SysLog sysLog = new SysLog();
        sysLog.setUserId(sysUser.getId());
        sysLog.setCreateDate(new Date());
        sysLog.setAction("logout");
        logService.saveSysLog(sysLog);
        resp.sendRedirect("index.jsp");
    }
}
