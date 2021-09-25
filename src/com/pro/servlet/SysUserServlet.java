package com.pro.servlet;

import com.pro.model.SysLog;
import com.pro.model.SysUser;
import com.pro.service.SysLogService;
import com.pro.service.SysUserService;
import com.pro.utils.Result;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

@WebServlet("/user")
public class SysUserServlet extends HttpServlet {

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
            case "save":
                save(req,resp);
                break;
            case "login":
                login(req,resp);
                break;
            case "register":
                register(req,resp);
                break;
            case "list":
                list(req,resp);
                break;
            case "update":
                update(req,resp);
                break;
            case "listUpdate":
                listUpdate(req,resp);
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
        SysUser sysUser = new SysUser();
        sysUser.setUsername(req.getParameter("username"));
        sysUser.setPassword(req.getParameter("password"));
        sysUser.setPhone(req.getParameter("phone"));
        sysUser.setRoleType(Integer.valueOf(req.getParameter("roleType")));
        sysUser.setStatus(Integer.valueOf(req.getParameter("status")));

        Result result = sysUserService.saveSysUser(sysUser);
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

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));
        Result result = sysUserService.deleteSysUser(id);
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

    private void info(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("info.jsp").forward(req,resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        SysUser sysUser = new SysUser();
        sysUser.setId(Integer.valueOf(req.getParameter("id")));
        sysUser.setUsername(req.getParameter("username"));
        sysUser.setPassword(req.getParameter("password"));
        sysUser.setPhone(req.getParameter("phone"));

        Result result = sysUserService.updateSysUser(sysUser);
        if(result.getCode() == 200){
            req.getSession().setAttribute("sysUser",sysUserService.getById(sysUser.getId()));
            resp.sendRedirect("info.jsp");
        }else{
            req.setAttribute("errMsg",result.getMsg());
            req.getRequestDispatcher("info.jsp").forward(req,resp);
        }
    }

    private void listUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        SysUser sysUser = new SysUser();
        sysUser.setId(Integer.valueOf(req.getParameter("id")));
        sysUser.setUsername(req.getParameter("username"));
        sysUser.setPassword(req.getParameter("password"));
        sysUser.setPhone(req.getParameter("phone"));
        sysUser.setRoleType(Integer.valueOf(req.getParameter("roleType")));
        sysUser.setStatus(Integer.valueOf(req.getParameter("status")));

        Result result = sysUserService.updateSysUser(sysUser);
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
        String username = req.getParameter("username");
        String phone = req.getParameter("phone");
        System.out.println("u:" + username);
        System.out.println("p:" + phone);

        List<SysUser> sysUserList = sysUserService.getSysUserList(username, phone);
        req.setAttribute("users",sysUserList);
        req.getRequestDispatcher("user.jsp").forward(req,resp);
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(req.getParameter("username"));
        sysUser.setPassword(req.getParameter("password"));
        sysUser.setPhone(req.getParameter("phone"));
        sysUser.setRoleType(Integer.valueOf(req.getParameter("roleType")));
        sysUser.setStatus(1);

        Result result = sysUserService.saveSysUser(sysUser);
        if(result.getCode() == 200){
            resp.sendRedirect("index.jsp");
        }else{
            req.setAttribute("errMsg",result.getMsg());
            req.getRequestDispatcher("register.jsp").forward(req,resp);
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(req.getParameter("username"));
        sysUser.setPassword(req.getParameter("password"));
        System.out.println(sysUser);
        Result result = sysUserService.login(sysUser);

        if(result.getCode() == 200){
            HttpSession session = req.getSession();
            SysUser user = (SysUser) result.getData();
            session.setAttribute("sysUser",user);

            SysLogService logService = new SysLogService();
            SysLog sysLog = new SysLog();
            sysLog.setUserId(user.getId());
            sysLog.setCreateDate(new Date());
            sysLog.setAction("login");
            logService.saveSysLog(sysLog);

            resp.sendRedirect("main.jsp");
        }else{
            req.setAttribute("errMsg",result.getMsg());
            req.getRequestDispatcher("index.jsp").forward(req,resp);
        }

    }


}
