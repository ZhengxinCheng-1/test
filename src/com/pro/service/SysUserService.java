package com.pro.service;

import java.util.ArrayList;
import java.util.List;

import com.pro.dao.OrdersDao;
import com.pro.dao.PaymentDao;
import com.pro.dao.SysLogDao;
import com.pro.model.SysUser;
import com.pro.dao.SysUserDao;
import com.pro.utils.Result;

public class SysUserService {

	private SysUserDao sysUserDao;

	public SysUserService() {
		this.sysUserDao = new SysUserDao();
	}
	
	public Result saveSysUser(SysUser sysUser) {
		int count = sysUserDao.checkPhone(sysUser.getPhone(),sysUser.getId());
		if(count > 0){
			return Result.failed("The phone already exists",500);
		}

		SysUser obj = sysUserDao.selectByUsername(sysUser.getUsername(),sysUser.getId());
		if(obj != null){
			return Result.failed("The username already exists",500);
		}

		sysUserDao.insert(sysUser);
		return Result.success();
	}

	public SysUser getById(Integer id){
		return sysUserDao.queryById(id);
	}
	
	public Result deleteSysUser(Integer id) {
		sysUserDao.delete(id);
		new OrdersDao().deleteByCustomerId(id);
		new PaymentDao().deleteByCustomerId(id);
		new SysLogDao().deleteByUserId(id);
		return Result.success();
	}
	
	public Result updateSysUser(SysUser sysUser) {
		int count = sysUserDao.checkPhone(sysUser.getPhone(),sysUser.getId());
		if(count > 0){
			return Result.failed("The phone already exists",500);
		}
		SysUser obj = sysUserDao.selectByUsername(sysUser.getUsername(),sysUser.getId());
		if(obj != null){
			return Result.failed("The username already exists",500);
		}

		sysUserDao.update(sysUser);
		return Result.success();
	}

	public Result login(SysUser sysUser){
		SysUser obj = sysUserDao.selectByUsername(sysUser.getUsername(),sysUser.getId());
		if(obj != null && sysUser.getPassword().equals(obj.password)){
			if(obj.getStatus() == 2){
				return Result.failed("The account has been frozen",500);
			}
			return Result.success(obj);
		}else{
			return Result.failed("The username or password error",500);
		}
	}
	
	public List<SysUser> getSysUserList(String username,String phone){
		String sql = "select *  from sys_user where 1=1";
		if(username != null && !"".equals(username)){
			sql += " and username='" + username + "'";
		}

		if(phone != null && !"".equals(phone)){
			sql += " and phone='" + phone + "'";
		}
		List<SysUser> sysUsers = sysUserDao.queryList(sql);
		return sysUsers;
	}
}
