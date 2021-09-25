package com.pro.service;

import java.util.List;

import com.pro.model.SysLog;
import com.pro.dao.SysLogDao;

public class SysLogService {

	private SysLogDao sysLogDao;

	public SysLogService() {
		this.sysLogDao = new SysLogDao();
	}
	
	public void saveSysLog(SysLog sysLog) {
		sysLogDao.insert(sysLog);
	}

	public List<SysLog> getSysLogList(Integer userId,String date){
		String sql = "select * from sys_log where 1=1";
		if(userId != null){
			sql += " and user_id=" + userId;
		}

		if(date != null && !"".equals(date)){
			sql += " and date_format(create_date,'%Y-%m-%d') ='" + date + "'";
		}
		sql += " order by create_date desc";
		return sysLogDao.queryList(sql);
	}
}
